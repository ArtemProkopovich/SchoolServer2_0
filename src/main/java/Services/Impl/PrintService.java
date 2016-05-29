package Services.Impl;

import DAO.DAOException;
import DAO.Interfacies.IUnitOfWork;
import Entities.Class;
import Entities.*;
import ServiceEntities.*;
import Services.Interfacies.IPrintService;
import Services.ServiceException;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.io.output.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.util.*;
import java.util.List;

/**
 * Created by Артем on 02.05.2016.
 */
public class PrintService implements IPrintService {

    private IUnitOfWork uof;

    public PrintService(IUnitOfWork uof) {
        this.uof = uof;
    }

    public InputStream PrintPDFAchivementStatistics(int pupilID) throws ServiceException {
        try {
            Pupil pupil = uof.getPupilDao().Select(pupilID);
            OutputStream stream = new FileOutputStream("achive.pdf");
            Document document = new Document();
            PdfWriter.getInstance(document,stream);
            document.open();
            AddPDFDocumentMeta(document,"Achievement statistics: "+pupil.getSurname()+" "+pupil.getName());
            AddPDFFirstPage(document, "Achievement statistics:" + pupil.getSurname()+" " +pupil.getName());
            PupilStats stats = GetPupilAchivementStatistics(pupil);

            PdfPTable table = new PdfPTable(3);
            PdfPCell c1 = new PdfPCell(new Phrase("Subject"));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);

            c1 = new PdfPCell(new Phrase("List of mark"));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);

            c1 = new PdfPCell(new Phrase("Average mark"));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);
            table.setHeaderRows(1);

            for (PupilSubjectStats pss : stats.getStatsList())
            {
                table.addCell(pss.getSubject().getName());
                table.addCell(GetMarksList(pss.getMarkList()));
                if (pss.getAverageMark()>0.0001) {
                    table.addCell(String.format("%.2f", pss.getAverageMark()));
                }
                else {
                    table.addCell("-");
                }
            }
            document.add(table);
            document.close();
            return new FileInputStream("achive.pdf");
        }
        catch (FileNotFoundException ex) {
            throw new ServiceException(ex);
        }
        catch (DAOException ex) {
            throw new ServiceException(ex);
        }
        catch (Exception ex) {
            throw new ServiceException(ex);
        }
    }

    private String GetMarksList(List<Mark> list)
    {
        StringBuilder result = new StringBuilder();
        for (int i=0;i<list.size();i++)
        {
            result.append(list.get(i).getMark()).append(",");
        }
        return result.toString();
    }

    public InputStream PrintXLSAchivementStatistics(int pupilID)throws ServiceException {
        try {
            Pupil pupil = uof.getPupilDao().Select(pupilID);
            PupilStats stats = GetPupilAchivementStatistics(pupil);
            int maxLength = GetMaxSize(stats.getStatsList());
            XSSFWorkbook book = new XSSFWorkbook();
            XSSFSheet sheet = book.createSheet("Achievement");

            XSSFRow row = sheet.createRow(0);

            XSSFCell name = row.createCell(0);
            name.setCellValue("Subject");
            sheet.autoSizeColumn(0);
            for (int i=1;i<maxLength;i++) {
                XSSFCell num = row.createCell(i);
                num.setCellValue(i);
                sheet.autoSizeColumn(i);
            }
            XSSFCell avM = row.createCell(maxLength);
            avM.setCellValue("Average mark");
            sheet.autoSizeColumn(maxLength);

            int rowIndex=1;
            for(PupilSubjectStats s : stats.getStatsList()) {
                row = sheet.createRow(rowIndex);
                XSSFCell cell = row.createCell(0);
                cell.setCellValue(s.getSubject().getName());
                for (int i = 0; i < s.getMarkList().size() - 1; i++) {
                    cell = row.createCell(i + 1);
                    cell.setCellValue(s.getMarkList().get(i).getMark());
                }
                if (s.getAverageMark() > 0.0001) {
                    cell = row.createCell(maxLength);
                    cell.setCellValue(s.getAverageMark());
                }
                rowIndex++;
            }

            book.write(new FileOutputStream("pas.xlsx"));
            book.close();
            return new FileInputStream("pas.xlsx");
        } catch (DAOException ex) {
            throw new ServiceException(ex);
        } catch (IOException ex) {
            throw new ServiceException(ex);
        }
    }

    private int GetMaxSize (List<PupilSubjectStats> list) {
        int length = 0;
        for (PupilSubjectStats s : list) {
            if (s.getMarkList().size() > length)
                length = s.getMarkList().size();
        }
        return length + 1;
    }

    private static final String NEW_LINE_SEPARATOR = "\n";
    public InputStream PrintCSVAchivementStatistics(int pupilID)throws ServiceException {
        try{
            Pupil pupil = uof.getPupilDao().Select(pupilID);
            CSVFormat csvFileFormat = CSVFormat.newFormat(';').withRecordSeparator(NEW_LINE_SEPARATOR);
            FileWriter fw = new FileWriter("pas.csv");
            CSVPrinter csvFilePrinter = new CSVPrinter(fw,csvFileFormat);
            PupilStats stats = GetPupilAchivementStatistics(pupil);
            int maxLength=GetMaxSize(stats.getStatsList());
            List headerList = new ArrayList();
            headerList.add("Subject");
            for (int i=1;i<maxLength;i++) {
                headerList.add(String.valueOf(i));
            }
            headerList.add("Average mark");
            csvFilePrinter.printRecord(headerList);

            for(PupilSubjectStats s : stats.getStatsList()) {
                List printList = new ArrayList();
                printList.add(s.getSubject().getName());
                for (int i = 0; i < maxLength - 1; i++) {
                    if (i < s.getMarkList().size())
                        printList.add(s.getMarkList().get(i).getMark());
                    else
                        printList.add("");
                }
                if (s.getAverageMark() > 0.0001) {
                    printList.add(s.getAverageMark());
                }
                csvFilePrinter.printRecord(printList);
            }

            fw.flush();
            fw.close();
            return new FileInputStream("pas.csv");
        }
        catch (DAOException ex){
            throw new ServiceException(ex);
        }
        catch(IOException ex) {
            throw new ServiceException(ex);
        }
    }

    private PupilStats GetPupilAchivementStatistics(Pupil pupil) throws DAOException {
        try {
            PupilStats result = new PupilStats();
            result.setPupil(pupil);
            List<PupilSubjectStats> subjectStats = new ArrayList<PupilSubjectStats>();
            List<Subject> subjectList = uof.getClassDao().GetClassSubjects(pupil.getClassID());
            double sumAv = 0.0;
            for (Subject s : subjectList) {
                PupilSubjectStats pss = new PupilSubjectStats();
                List<Mark> marksList = uof.getMarkDao().GetPupilMarksBySubjectID(s.getID(), pupil.getID());
                double sum = 0.0;
                int count = 0;
                for (Mark m : marksList) {
                    if (m.getMark() > 0) {
                        sum += m.getMark();
                        count++;
                    }
                }
                pss.setMarkList(marksList);
                pss.setSubject(s);
                if (count > 0) {
                    sumAv += sum / count;
                    pss.setAverageMark(sum / count);
                }
                subjectStats.add(pss);
            }
            if (subjectList.size() > 0)
                result.setAverageMark(sumAv / subjectList.size());
            result.setStatsList(subjectStats);
            return result;
        } catch (DAOException ex) {
            throw ex;
        }
    }

    public InputStream PrintPDFPupilsRating(int classID) throws ServiceException {
        try {
            Class cls = uof.getClassDao().Select(classID);
            List<Subject> subjects = uof.getClassDao().GetClassSubjects(classID);
            List<PupilStats> pupilStats = GetPupilsStats(classID);
            OutputStream stream = new FileOutputStream("prs.pdf");
            Document document = new Document();
            PdfWriter.getInstance(document, stream);
            document.open();
            AddPDFDocumentMeta(document, "Rating of pupils by: " + cls.getName()+" class.");
            AddPDFFirstPage(document, "Rating of pupils by:" + cls.getName()+" class.");

            document.add(new Paragraph("Class: "+cls.getName()+".", redFont));
            document.add(new Paragraph(" "));
            PdfPTable table = new PdfPTable(subjects.size()+2);
            PdfPCell c1 = new PdfPCell(new Phrase("Pupil"));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);

            DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
            for (Subject s: subjects)
            {
                c1 = new PdfPCell(new Phrase(s.getName()));
                c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(c1);
            }
            c1 = new PdfPCell(new Phrase("Rating"));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);
            table.setHeaderRows(1);
            for(int i=0;i<pupilStats.size();i++) {
                table.addCell(String.valueOf(i + 1) + ". " + pupilStats.get(i).getPupil().getSurname() + " " + pupilStats.get(i).getPupil().getName());
                double sum = 0.0;
                for (PupilSubjectStats pss : pupilStats.get(i).getStatsList()) {
                    table.addCell(String.format("%.2f", pss.getAverageMark()));
                    sum += pss.getAverageMark();
                }
                if (pupilStats.get(i).getStatsList().size() > 0)
                    table.addCell(String.format("%.2f", sum / pupilStats.get(i).getStatsList().size()));
                else
                    table.addCell("-");
            }
            document.add(table);
            document.close();
            return new FileInputStream("prs.pdf");

        } catch (FileNotFoundException ex) {
            throw new ServiceException(ex);
        } catch (DAOException ex) {
            throw new ServiceException(ex);
        } catch (Exception ex) {
            throw new ServiceException(ex);
        }
    }

    public InputStream PrintXLSPupilsRating(int classID) throws ServiceException {
        try {
            Class cls = uof.getClassDao().Select(classID);
            List<Subject> subjects = uof.getClassDao().GetClassSubjects(classID);
            List<PupilStats> pupilStats = GetPupilsStats(classID);
            OutputStream stream = new FileOutputStream("prs.xlsx");
            XSSFWorkbook book = new XSSFWorkbook();
            XSSFSheet sheet = book.createSheet(cls.getName());
            XSSFRow row  = sheet.createRow(0);
            XSSFCell cell = row.createCell(0);
            cell.setCellValue("Pupil/Subjects");
            int index=1;
            for(Subject s : subjects) {
                cell = row.createCell(index);
                cell.setCellValue(s.getName());
                sheet.autoSizeColumn(index);
                index++;
            }
            cell = row.createCell(index);
            cell.setCellValue("Rating");
            sheet.autoSizeColumn(index);

            int rowIndex=1;
            for(PupilStats ps : pupilStats) {
                row = sheet.createRow(rowIndex);
                cell = row.createCell(0);
                cell.setCellValue(ps.getPupil().getSurname() + " " + ps.getPupil().getName());
                int columnIndex = 1;
                double sum = 0.0;
                for (PupilSubjectStats pss : ps.getStatsList()) {
                    cell = row.createCell(columnIndex);
                    cell.setCellValue(String.format("%.2f", pss.getAverageMark()));
                    sum += pss.getAverageMark();
                    columnIndex++;
                }
                cell = row.createCell(columnIndex);
                if (pupilStats.get(rowIndex - 1).getStatsList().size() > 0)
                    cell.setCellValue(String.format("%.2f", sum / pupilStats.get(rowIndex - 1).getStatsList().size()));
                else
                    cell.setCellValue("-");
                rowIndex++;
            }
            sheet.autoSizeColumn(0);
            book.write(new FileOutputStream("prs.xlsx"));
            book.close();
            return new FileInputStream("prs.xlsx");

        } catch (DAOException ex) {
            throw new ServiceException(ex);
        } catch (IOException ex) {
            throw new ServiceException(ex);
        }
    }

    public InputStream PrintCSVPupilsRating(int classID) throws ServiceException {
        try {
            Class cls = uof.getClassDao().Select(classID);
            List<Subject> subjects = uof.getClassDao().GetClassSubjects(classID);
            List<PupilStats> pupilStats = GetPupilsStats(classID);
            CSVFormat csvFileFormat = CSVFormat.newFormat(';').withRecordSeparator(NEW_LINE_SEPARATOR);
            FileWriter fw = new FileWriter("prs.csv");
            CSVPrinter csvFilePrinter = new CSVPrinter(fw,csvFileFormat);
            List headerList = new ArrayList();
            headerList.add("Pupil/Subjects");
            for(Subject s : subjects) {
                headerList.add(s.getName());
            }
            headerList.add("Rating");
            csvFilePrinter.printRecord(headerList);

            int rowIndex=1;
            for(PupilStats ps : pupilStats) {
                headerList= new ArrayList();
                headerList.add(ps.getPupil().getSurname() + " " + ps.getPupil().getName());
                double sum = 0.0;
                for (PupilSubjectStats pss : ps.getStatsList()) {
                    headerList.add(String.format("%.2f", pss.getAverageMark()));
                    sum += pss.getAverageMark();
                }
                if (pupilStats.get(rowIndex - 1).getStatsList().size() > 0)
                    headerList.add(String.format("%.2f", sum / pupilStats.get(rowIndex - 1).getStatsList().size()));
                else
                    headerList.add("-");
                rowIndex++;
                csvFilePrinter.printRecord(headerList);
            }
            fw.flush();
            fw.close();
            return new FileInputStream("prs.csv");

        } catch (DAOException ex) {
            throw new ServiceException(ex);
        } catch (IOException ex) {
            throw new ServiceException(ex);
        }
    }

    private List<PupilStats> GetPupilsStats(int classID) throws DAOException {
        List<PupilStats> result = new ArrayList<PupilStats>();
        List<Pupil> pupils = uof.getClassDao().GetClassPupilList(classID);
        for (Pupil p : pupils) {
            result.add(GetPupilAchivementStatistics(p));
        }
        return result;
    }

    public InputStream PrintPDFSubjectList(int subjectID) throws ServiceException {
        try {
            Subject subject = uof.getSubjectDao().Select(subjectID);
            SubjectJournalList sjl = GetSubjectJournalInfo(subject);
            OutputStream stream = new FileOutputStream("sjl.pdf");
            Document document = new Document();
            PdfWriter.getInstance(document, stream);
            document.open();
            AddPDFDocumentMeta(document, "Subject journal table: " + subject.getName());
            AddPDFFirstPage(document, "Subject journal table:" + subject.getName());

            document.add(new Paragraph("Subject: "+subject.getName()+". Teacher: "+sjl.getTeacher().getSurname()+" "+sjl.getTeacher().getName()+". Class: "+sjl.getCls().getName()+".", redFont));
            document.add(new Paragraph(" "));
            PdfPTable table = new PdfPTable(sjl.getLessonList().size()+1);
            PdfPCell c1 = new PdfPCell(new Phrase("Pupil"));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);

            DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
            for (Lesson l : sjl.getLessonList())
            {
                c1 = new PdfPCell(new Phrase(df.format(l.getDate())));
                c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(c1);
            }
            table.setHeaderRows(1);
            for(int i=0;i<sjl.getPupilList().size();i++) {
                table.addCell(String.valueOf(i+1)+". "+sjl.getPupilList().get(i).getSurname()+" "+sjl.getPupilList().get(i).getName());
                for (Lesson l : sjl.getLessonList())
                {
                    Mark mark = null;
                    for (Mark m: sjl.getMarksList().get(i))
                    {
                        if (m.getLessonID()==l.getID())
                            mark = m;
                    }
                    if (mark!=null)
                    {
                        if (mark.getMark()>0)
                            table.addCell(String.valueOf(mark.getMark()));
                        else
                            table.addCell("a");
                    }
                    else
                        table.addCell("");
                }
            }
            document.add(table);
            document.close();
            return new FileInputStream("sjl.pdf");

        } catch (FileNotFoundException ex) {
            throw new ServiceException(ex);
        } catch (DAOException ex) {
            throw new ServiceException(ex);
        } catch (Exception ex) {
            throw new ServiceException(ex);
        }
    }

    public InputStream PrintXLSSubjectList(int subjectID) throws ServiceException {
        try{
            Subject subject = uof.getSubjectDao().Select(subjectID);
            SubjectJournalList sjl = GetSubjectJournalInfo(subject);
            XSSFWorkbook book = new XSSFWorkbook();
            XSSFSheet sheet = book.createSheet(sjl.getSubject().getName());
            XSSFRow row  = sheet.createRow(0);
            XSSFCell cell = row.createCell(0);
            sheet.autoSizeColumn(0);
            cell.setCellValue("Pupil/Date");
            int index=1;
            DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
            for(Lesson l : sjl.getLessonList()) {
                cell = row.createCell(index);
                cell.setCellValue(df.format(l.getDate()));
                sheet.autoSizeColumn(index);
                index++;
            }

            int rowIndex=1;
            for(Pupil p : sjl.getPupilList())
            {
                row = sheet.createRow(rowIndex);
                cell = row.createCell(0);
                cell.setCellValue(p.getSurname()+" "+p.getName());
                int columnIndex=1;
                for (Lesson l : sjl.getLessonList())
                {
                    cell = row.createCell(columnIndex);
                    Mark mark = null;
                    for (Mark m: sjl.getMarksList().get(rowIndex-1))
                    {
                        if (m.getLessonID()==l.getID())
                            mark = m;
                    }
                    if (mark!=null)
                    {
                        if (mark.getMark()>0) {
                            cell.setCellValue(String.valueOf(mark.getMark()));
                        }
                        else
                            cell.setCellValue("a");
                    }
                    else
                        cell.setCellValue("");
                    columnIndex++;
                }
                rowIndex++;
            }
            sheet.autoSizeColumn(0);
            book.write(new FileOutputStream("sjl.xlsx"));
            book.close();
            return new FileInputStream("sjl.xlsx");
        }
        catch(DAOException ex){
            throw new ServiceException(ex);
        }
        catch (IOException ex) {
            throw new ServiceException(ex);
        }
    }

    public InputStream PrintCSVSubjectList(int subjectID) throws ServiceException {
        try{
            Subject subject = uof.getSubjectDao().Select(subjectID);
            CSVFormat csvFileFormat = CSVFormat.newFormat(';').withRecordSeparator(NEW_LINE_SEPARATOR);
            FileWriter fw = new FileWriter("sjl.csv");
            CSVPrinter csvFilePrinter = new CSVPrinter(fw,csvFileFormat);
            SubjectJournalList sjl = GetSubjectJournalInfo(subject);
            List headerList = new ArrayList();
            DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
            headerList.add("Pupil/Date");
            for(Lesson l : sjl.getLessonList())
            {
                headerList.add(df.format(l.getDate()));
            }
            csvFilePrinter.printRecord(headerList);

            int rowIndex=1;
            for(Pupil p : sjl.getPupilList())
            {
                List printList = new ArrayList();
                printList.add(p.getSurname()+" "+p.getName());
                for (Lesson l : sjl.getLessonList())
                {
                    Mark mark = null;
                    for (Mark m: sjl.getMarksList().get(rowIndex-1))
                    {
                        if (m.getLessonID()==l.getID())
                            mark = m;
                    }
                    if (mark!=null)
                    {
                        if (mark.getMark()>0) {
                            printList.add(String.valueOf(mark.getMark()));
                        }
                        else
                            printList.add("a");
                    }
                    else
                        printList.add("");;
                }
                csvFilePrinter.printRecord(printList);
                rowIndex++;
            }
            fw.flush();
            fw.close();
            return new FileInputStream("sjl.csv");
        }
        catch (DAOException ex){
            throw new ServiceException(ex);
        }
        catch(IOException ex) {
            throw new ServiceException(ex);
        }
    }

    private SubjectJournalList GetSubjectJournalInfo(Subject subject)throws DAOException
    {
        try{
            SubjectJournalList sjl = new SubjectJournalList();
            sjl.setSubject(subject);
            sjl.setTeacher(uof.getTeacherDao().Select(subject.getTeacherID()));
            sjl.setCls(uof.getClassDao().Select(subject.getClassID()));
            sjl.setLessonList(uof.getLessonDao().GetSubjectLessons(subject.getID()));
            sjl.setPupilList(uof.getSubjectDao().GetSubjectPupils(subject.getID()));
            List<List<Mark>> marksList = new ArrayList<List<Mark>>();
            for (Pupil p : sjl.getPupilList())
            {
                List<Mark> pupilMarkList = uof.getMarkDao().GetPupilMarksBySubjectID(subject.getID(),p.getID());
                marksList.add(pupilMarkList);
            }
            sjl.setMarksList(marksList);
            return sjl;
        }
        catch (DAOException ex) {
            throw ex;
        }
    }

    public InputStream PrintPDFTeacherWeekSchedule(int teacherID) throws ServiceException {
        try{
            Teacher teacher = uof.getTeacherDao().Select(teacherID);
            List<List<ScheduleTeacherLesson>> weekScheduleList = GetTeacherWeekSchedule(teacher);
            OutputStream stream = new FileOutputStream("tws.pdf");
            Document document = new Document();
            PdfWriter.getInstance(document,stream);
            document.open();
            AddPDFDocumentMeta(document,"Teacher schedule: "+teacher.getSurname()+" "+teacher.getName());
            AddPDFFirstPage(document, "Teacher schedule:" + teacher.getSurname()+" " +teacher.getName());

            int i=1;
            for (List<ScheduleTeacherLesson> stlList:weekScheduleList) {
                DayOfWeek dof =  DayOfWeek.of(i);
                document.add(new Paragraph(i+". "+ dof.toString(),redFont));
                PdfPTable table = new PdfPTable(4);
                PdfPCell c1 = new PdfPCell(new Phrase("Number of lesson"));
                c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(c1);
                c1 = new PdfPCell(new Phrase("Subject"));
                c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(c1);
                c1 = new PdfPCell(new Phrase("Class"));
                c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(c1);
                c1 = new PdfPCell(new Phrase("Room"));
                c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(c1);
                table.setHeaderRows(1);
                for (ScheduleTeacherLesson stl:stlList)
                {
                    table.addCell(String.valueOf(stl.getLesson().getID()));
                    table.addCell(stl.getSubject().getName());
                    table.addCell(stl.getCls().getName());
                    table.addCell(String.valueOf(stl.getLesson().getRoom()));
                }
                document.add(table);
                document.add(new Paragraph(""));
                i++;
            }
            document.close();
            return new FileInputStream("tws.pdf");

        }
        catch (FileNotFoundException ex)
        {
            throw new ServiceException(ex);
        }
        catch (DAOException ex)
        {
            throw new ServiceException(ex);
        }
        catch (Exception ex) {
            throw new ServiceException(ex);
        }
    }

    public InputStream PrintXLSTeacherWeekSchedule(int teacherID) throws ServiceException {
        try{
            Teacher teacher = uof.getTeacherDao().Select(teacherID);
            List<List<ScheduleTeacherLesson>> weekScheduleList = GetTeacherWeekSchedule(teacher);
            XSSFWorkbook book = new XSSFWorkbook();
            XSSFSheet sheet = book.createSheet(teacher.getSurname()+" "+teacher.getName()+" schedule");

            int dayIndex=1;
            int rowIndex=0;
            for(int i=0;i<4;i++) {
                sheet.autoSizeColumn(i);
            }
            for(List<ScheduleTeacherLesson> spl:weekScheduleList)
            {
                DayOfWeek dof = DayOfWeek.of(dayIndex);
                XSSFRow row = sheet.createRow(rowIndex);
                XSSFCell cell = row.createCell(0);
                cell.setCellValue(dof.toString());
                rowIndex++;
                row = sheet.createRow(rowIndex);
                cell = row.createCell(0);
                cell.setCellValue("N");
                cell = row.createCell(1);
                cell.setCellValue("Subject");
                cell = row.createCell(2);
                cell.setCellValue("Class");
                cell = row.createCell(3);
                cell.setCellValue("Room");
                rowIndex++;
                for(ScheduleTeacherLesson pl: spl)
                {
                    row = sheet.createRow(rowIndex);
                    cell = row.createCell(0);
                    cell.setCellValue(pl.getLesson().getScheduleNumber());
                    cell = row.createCell(1);
                    cell.setCellValue(pl.getSubject().getName());
                    cell = row.createCell(2);
                    cell.setCellValue(pl.getCls().getName());
                    cell = row.createCell(3);
                    cell.setCellValue(pl.getLesson().getRoom());
                    rowIndex++;
                }
                dayIndex++;
                rowIndex++;
            }

            book.write(new FileOutputStream("tws.xlsx"));
            book.close();
            return new FileInputStream("tws.xlsx");
        }
        catch(DAOException ex){
            throw new ServiceException(ex);
        }
        catch (IOException ex) {
            throw new ServiceException(ex);
        }
    }

    public InputStream PrintCSVTeacherWeekSchedule(int teacherID) throws ServiceException {
        try{
            Teacher teacher = uof.getTeacherDao().Select(teacherID);
            List<List<ScheduleTeacherLesson>> weekScheduleList = GetTeacherWeekSchedule(teacher);
            CSVFormat csvFileFormat = CSVFormat.newFormat(';').withRecordSeparator(NEW_LINE_SEPARATOR);
            FileWriter fw = new FileWriter("tws.csv");
            CSVPrinter csvFilePrinter = new CSVPrinter(fw,csvFileFormat);

            int dayIndex=1;
            for(List<ScheduleTeacherLesson> spl:weekScheduleList)
            {
                DayOfWeek dof = DayOfWeek.of(dayIndex);
                List printList = new ArrayList();
                printList.add(dof.toString());
                csvFilePrinter.printRecord(printList);
                printList = new ArrayList();
                printList.add("N");
                printList.add("Subject");
                printList.add("Class");
                printList.add("Room");
                csvFilePrinter.printRecord(printList);
                for(ScheduleTeacherLesson pl: spl)
                {
                    printList = new ArrayList();
                    printList.add(pl.getLesson().getScheduleNumber());
                    printList.add(pl.getSubject().getName());
                    printList.add(pl.getCls().getName());
                    printList.add(pl.getLesson().getRoom());
                    csvFilePrinter.printRecord(printList);
                }
                dayIndex++;
                printList.add(new ArrayList());
            }

            fw.flush();
            fw.close();
            return new FileInputStream("tws.csv");
        }
        catch(DAOException ex){
            throw new ServiceException(ex);
        }
        catch (IOException ex) {
            throw new ServiceException(ex);
        }
    }

    private List<List<ScheduleTeacherLesson>> GetTeacherWeekSchedule(Teacher teacher) throws DAOException
    {
        try{
            Calendar startDate = new GregorianCalendar(2016, 04, 01);
            while (startDate.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY)
                startDate.add(Calendar.DAY_OF_MONTH, 1);
            List<List<ScheduleTeacherLesson>> resultList = new ArrayList<List<ScheduleTeacherLesson>>();
            for(int i=0;i<7;i++) {
                List<ScheduleTeacherLesson> dayLessonList = new ArrayList<ScheduleTeacherLesson>();
                List<Lesson> lessonList = uof.getLessonDao().GetTeacherDayLessons(teacher.getID(), startDate.getTime());
                for (Lesson l : lessonList) {
                    Subject subject = uof.getSubjectDao().Select(l.getSubjectID());
                    Class cls = uof.getClassDao().Select(subject.getClassID());
                    ScheduleTeacherLesson stl = new ScheduleTeacherLesson();
                    stl.setLesson(l);
                    stl.setSubject(subject);
                    stl.setCls(cls);
                    dayLessonList.add(stl);
                }
                resultList.add(dayLessonList);
                startDate.add(Calendar.DAY_OF_YEAR, 1);
            }
            return resultList;
        }
        catch (DAOException ex){
            throw ex;
        }
    }

    public InputStream PrintPDFPupilWeekSchedule(int pupilID) throws ServiceException {
        try{
            Pupil pupil = uof.getPupilDao().Select(pupilID);
            List<List<SchedulePupilLesson>> weekScheduleList = GetPupilWeekSchedule(pupil);
            OutputStream stream = new FileOutputStream("pws.pdf");
            Document document = new Document();
            PdfWriter.getInstance(document,stream);
            document.open();
            AddPDFDocumentMeta(document,"Pupil schedule: "+pupil.getSurname()+" "+pupil.getName());
            AddPDFFirstPage(document, "Pupil schedule:" + pupil.getSurname()+" " +pupil.getName());

            int i=1;
            for (List<SchedulePupilLesson> stlList:weekScheduleList) {
                DayOfWeek dof =  DayOfWeek.of(i);
                document.add(new Paragraph(i+". "+ dof.toString(),redFont));
                PdfPTable table = new PdfPTable(4);
                PdfPCell c1 = new PdfPCell(new Phrase("Number of lesson"));
                c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(c1);
                c1 = new PdfPCell(new Phrase("Subject"));
                c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(c1);
                c1 = new PdfPCell(new Phrase("Teacher"));
                c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(c1);
                c1 = new PdfPCell(new Phrase("Room"));
                c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(c1);
                table.setHeaderRows(1);
                for (SchedulePupilLesson stl:stlList)
                {
                    table.addCell(String.valueOf(stl.getLesson().getID()));
                    table.addCell(stl.getSubject().getName());
                    table.addCell(stl.getTeacher().getSurname()+" "+stl.getTeacher().getName());
                    table.addCell(String.valueOf(stl.getLesson().getRoom()));
                }
                document.add(table);
                document.add(new Paragraph(""));
                i++;
            }
            document.close();
            return new FileInputStream("pws.pdf");

        }
        catch (FileNotFoundException ex)
        {
            throw new ServiceException(ex);
        }
        catch (DAOException ex)
        {
            throw new ServiceException(ex);
        }
        catch (Exception ex) {
            throw new ServiceException(ex);
        }
    }

    public InputStream PrintCSVPupilWeekSchedule(int pupilID) throws ServiceException {
        try{
            Pupil pupil = uof.getPupilDao().Select(pupilID);
            List<List<SchedulePupilLesson>> weekScheduleList = GetPupilWeekSchedule(pupil);
            CSVFormat csvFileFormat = CSVFormat.newFormat(';').withRecordSeparator(NEW_LINE_SEPARATOR);
            FileWriter fw = new FileWriter("pws.csv");
            CSVPrinter csvFilePrinter = new CSVPrinter(fw,csvFileFormat);

            int dayIndex=1;
            for(List<SchedulePupilLesson> spl:weekScheduleList)
            {
                DayOfWeek dof = DayOfWeek.of(dayIndex);
                List printList = new ArrayList();
                printList.add(dof.toString());
                csvFilePrinter.printRecord(printList);
                printList = new ArrayList();
                printList.add("N");
                printList.add("Subject");
                printList.add("Teacher");
                printList.add("Room");
                csvFilePrinter.printRecord(printList);
                for(SchedulePupilLesson pl: spl)
                {
                    printList = new ArrayList();
                    printList.add(pl.getLesson().getScheduleNumber());
                    printList.add(pl.getSubject().getName());
                    printList.add(pl.getTeacher().getSurname()+" "+ pl.getTeacher().getName());
                    printList.add(pl.getLesson().getRoom());
                    csvFilePrinter.printRecord(printList);
                }
                dayIndex++;
                printList.add(new ArrayList());
            }

            fw.flush();
            fw.close();
            return new FileInputStream("pws.csv");
        }
        catch(DAOException ex){
            throw new ServiceException(ex);
        }
        catch (IOException ex) {
            throw new ServiceException(ex);
        }
    }

    public InputStream PrintXLSPupilWeekSchedule(int pupilID) throws ServiceException {
        try{
            Pupil pupil = uof.getPupilDao().Select(pupilID);
            List<List<SchedulePupilLesson>> weekScheduleList = GetPupilWeekSchedule(pupil);
            XSSFWorkbook book = new XSSFWorkbook();
            XSSFSheet sheet = book.createSheet(pupil.getSurname()+" "+pupil.getName()+" schedule");

            int dayIndex=1;
            int rowIndex=0;
            for(int i=0;i<4;i++) {
                sheet.autoSizeColumn(i);
            }
            for(List<SchedulePupilLesson> spl:weekScheduleList)
            {
                DayOfWeek dof = DayOfWeek.of(dayIndex);
                XSSFRow row = sheet.createRow(rowIndex);
                XSSFCell cell = row.createCell(0);
                cell.setCellValue(dof.toString());
                rowIndex++;
                row = sheet.createRow(rowIndex);
                cell = row.createCell(0);
                cell.setCellValue("N");
                cell = row.createCell(1);
                cell.setCellValue("Subject");
                cell = row.createCell(2);
                cell.setCellValue("Teacher");
                cell = row.createCell(3);
                cell.setCellValue("Room");
                rowIndex++;
                for(SchedulePupilLesson pl: spl)
                {
                    row = sheet.createRow(rowIndex);
                    cell = row.createCell(0);
                    cell.setCellValue(pl.getLesson().getScheduleNumber());
                    cell = row.createCell(1);
                    cell.setCellValue(pl.getSubject().getName());
                    cell = row.createCell(2);
                    cell.setCellValue(pl.getTeacher().getSurname()+" "+ pl.getTeacher().getName());
                    cell = row.createCell(3);
                    cell.setCellValue(pl.getLesson().getRoom());
                    rowIndex++;
                }
                dayIndex++;
                rowIndex++;
            }

            book.write(new FileOutputStream("pws.xlsx"));
            book.close();
            return new FileInputStream("pws.xlsx");
        }
        catch(DAOException ex){
            throw new ServiceException(ex);
        }
        catch (IOException ex) {
            throw new ServiceException(ex);
        }
    }

    private List<List<SchedulePupilLesson>> GetPupilWeekSchedule(Pupil pupil) throws DAOException
    {
        try{
            List<List<SchedulePupilLesson>> resultList = new ArrayList<List<SchedulePupilLesson>>();
            Calendar startDate = new GregorianCalendar(2016, 04, 01);
            while (startDate.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY)
                startDate.add(Calendar.DAY_OF_MONTH, 1);
            for(int i=0;i<7;i++) {
                List<SchedulePupilLesson> dayLessonList = new ArrayList<SchedulePupilLesson>();
                List<Lesson> lessonList = uof.getLessonDao().GetPupilDayLessons(pupil.getID(), startDate.getTime());
                for (Lesson l : lessonList) {
                    Subject subject = uof.getSubjectDao().Select(l.getSubjectID());
                    Teacher teacher = uof.getTeacherDao().Select(subject.getTeacherID());
                    if (teacher==null)
                    {
                        teacher = new Teacher();
                        teacher.setName("");
                        teacher.setSurname("");
                    }
                    SchedulePupilLesson spl = new SchedulePupilLesson();
                    spl.setLesson(l);
                    spl.setSubject(subject);
                    spl.setTeacher(teacher);
                    dayLessonList.add(spl);
                }
                resultList.add(dayLessonList);
                startDate.add(Calendar.DAY_OF_YEAR, 1);
            }
            return resultList;
        }
        catch (DAOException ex){
            throw ex;
        }
    }

    private void AddPDFDocumentMeta(Document document, String title)
    {
        document.addTitle(title);
        document.addSubject("School Document System");
        document.addKeywords("Java, PDF, iText, SchoolServer, SPPTrueTeam");
        document.addAuthor("SPPTrueTeam");
        document.addCreator("SPPTrueTeam");
    }

    private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,
            Font.BOLD);
    private static Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.NORMAL, BaseColor.RED);
    private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16,
            Font.BOLD);
    private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.BOLD);

    private void AddPDFFirstPage(Document document, String docTitle) throws DocumentException{
        Paragraph preface = new Paragraph();
        addEmptyLine(preface, 1);
        preface.add(new Paragraph("Title of the document", catFont));
        addEmptyLine(preface, 1);
        preface.add(new Paragraph("Report generated by: " + "SPPTrueTeam"+ ", " + new Date(), smallBold));
        document.add(preface);
        document.newPage();
    }

    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }

}
