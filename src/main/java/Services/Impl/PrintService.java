package Services.Impl;

import DAO.DAOException;
import DAO.Interfacies.IUnitOfWork;
import Entities.Class;
import Entities.*;
import ServiceEntities.SchedulePupilLesson;
import ServiceEntities.ScheduleTeacherLesson;
import ServiceEntities.SubjectJournalList;
import Services.Interfacies.IPrintService;
import Services.ServiceException;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
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

    public InputStream PrintPDFAchivementStatistics(Pupil pupil) throws ServiceException {
        try {
            OutputStream stream = new FileOutputStream("achive.pdf");
            Document document = new Document();
            PdfWriter.getInstance(document,stream);
            document.open();
            AddPDFDocumentMeta(document,"Achievement statistics: "+pupil.getSurname()+" "+pupil.getName());
            AddPDFFirstPage(document, "Achievement statistics:" + pupil.getSurname()+" " +pupil.getName());
            Map<Subject, List<Mark>> achiveMap = GetPupilAchivementStatistics(pupil);

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

            Set<Map.Entry<Subject, List<Mark>>> set = achiveMap.entrySet();
            for (Map.Entry<Subject, List<Mark>> s : set)
            {
                table.addCell(s.getKey().getName());
                table.addCell(GetMarksList(s.getValue()));
                if (s.getValue().size()>0) {
                    table.addCell(String.valueOf(s.getValue().get(s.getValue().size() - 1).getMark()));
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
        catch (DocumentException ex) {
            throw new ServiceException(ex);
        }
    }

    private String GetMarksList(List<Mark> list)
    {
        StringBuilder result = new StringBuilder();
        for (int i=0;i<list.size()-1;i++)
        {
            result.append(list.get(i).getMark()).append(",");
        }
        if (list.size()>0) {
            result.delete(result.length() - 1, result.length() - 1);
            result.append(".");
        }
        return result.toString();
    }

    public InputStream PrintXLSAchivementStatistics(Pupil pupil)throws ServiceException {
        try {
            Map<Subject, List<Mark>> achieveMap = GetPupilAchivementStatistics(pupil);
            int maxLength = GetMaxSize(achieveMap);
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

            Set<Map.Entry<Subject, List<Mark>>> set = achieveMap.entrySet();
            int rowIndex=1;
            for(Map.Entry<Subject, List<Mark>> s : set) {
                row = sheet.createRow(rowIndex);
                XSSFCell cell = row.createCell(0);
                cell.setCellValue(s.getKey().getName());
                for (int i = 0; i < s.getValue().size() - 1; i++) {
                    cell = row.createCell(i + 1);
                    cell.setCellValue(s.getValue().get(i).getMark());
                }
                if (s.getValue().size() > 0) {
                    cell = row.createCell(maxLength);
                    cell.setCellValue(s.getValue().get(s.getValue().size() - 1).getMark());
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

    private int GetMaxSize (Map<Subject, List<Mark>> map)
    {
        Set<Map.Entry<Subject, List<Mark>>> set = map.entrySet();
        int length=0;
        for(Map.Entry<Subject, List<Mark>> s : set){
            if (s.getValue().size()>length)
                length=s.getValue().size();
        }
        return length;
    }

    private static final String NEW_LINE_SEPARATOR = "\n";
    public InputStream PrintCSVAchivementStatistics(Pupil pupil)throws ServiceException {
        try{
            CSVFormat csvFileFormat = CSVFormat.DEFAULT.withRecordSeparator(NEW_LINE_SEPARATOR);
            FileWriter fw = new FileWriter("pas.csv");
            CSVPrinter csvFilePrinter = new CSVPrinter(fw,csvFileFormat);
            Map<Subject, List<Mark>> achieveMap = GetPupilAchivementStatistics(pupil);
            int maxLength=GetMaxSize(achieveMap);
            List headerList = new ArrayList();
            headerList.add("Subject");
            for (int i=1;i<maxLength;i++) {
                headerList.add(String.valueOf(i));
            }
            headerList.add("Average mark");
            csvFilePrinter.printRecord(headerList);

            Set<Map.Entry<Subject, List<Mark>>> set = achieveMap.entrySet();
            for(Map.Entry<Subject, List<Mark>> s : set) {
                List printList = new ArrayList();
                printList.add(s.getKey().getName());
                for (int i = 0; i < maxLength-1; i++) {
                    if (i < s.getValue().size())
                        printList.add(s.getValue().get(i).getMark());
                    else
                        printList.add("");
                }
                if (s.getValue().size() > 0) {
                    printList.add(s.getValue().get(s.getValue().size() - 1).getMark());
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

    private Map<Subject,List<Mark>> GetPupilAchivementStatistics(Pupil pupil) throws DAOException{
        try {
            Map<Subject,List<Mark>> resultList = new HashMap<Subject, List<Mark>>();
            List<Subject> subjectList = uof.getPupilDao().GetPupilSubjects(pupil.getID());
            for (Subject s : subjectList){
                List<Mark> marksList = uof.getMarkDao().GetPupilMarksBySubjectID(s.getID(),pupil.getID());
                List<Mark> resultMarksList = new ArrayList<Mark>();
                int sum = 0;
                int count = 0;
                for (Mark m : marksList){
                    if (m.getMark()>0)
                    {
                        sum+=m.getMark();
                        count++;
                        resultMarksList.add(m);
                    }
                }
                if (count>0) {
                    Mark avMark  = new Mark();
                    avMark.setPupilID(pupil.getID());
                    avMark.setMark(sum/count);
                    resultMarksList.add(avMark);
                }
                resultList.put(s,resultMarksList);
            }
            return resultList;
        }
        catch (DAOException ex)
        {
            throw ex;
        }
    }



    public InputStream PrintPDFThanksLetter(Pupil pupil, Teacher teacher) throws ServiceException {
        return null;
    }

    public InputStream PrintXLSThanksLetter(Pupil pupil, Teacher teacher) throws ServiceException {
        return null;
    }

    public InputStream PrintCSVThanksLetter(Pupil pupil, Teacher teacher) throws ServiceException {
        return null;
    }

    public InputStream PrintPDFSubjectList(Subject subject) throws ServiceException {
        try {
            SubjectJournalList sjl = GetSubjectJournalInfo(subject);
            OutputStream stream = new FileOutputStream("sjl.pdf");
            Document document = new Document();
            PdfWriter.getInstance(document, stream);
            document.open();
            AddPDFDocumentMeta(document, "Subject journal table: " + subject.getName());
            AddPDFFirstPage(document, "Subject journal table:" + subject.getName());

            document.add(new Paragraph("Subject: "+subject.getName()+". Teacher: "+sjl.getTeacher().getSurname()+" "+sjl.getTeacher().getName()+". Class: "+sjl.getCls().getName()+".", redFont));
            PdfPTable table = new PdfPTable(sjl.getLessonList().size()+1);
            PdfPCell c1 = new PdfPCell(new Phrase("Pupil"));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);
            for (Lesson l : sjl.getLessonList())
            {
                c1 = new PdfPCell(new Phrase(l.getDate().getDay()+"."+ l.getDate().getMonth()));
                c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(c1);
            }
            table.setHeaderRows(1);
            for(int i=0;i<sjl.getPupilList().size();i++) {
                table.addCell(String.valueOf(i+1)+". "+sjl.getPupilList().get(i).getSurname()+sjl.getPupilList().get(i).getSurname());
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
        } catch (DocumentException ex) {
            throw new ServiceException(ex);
        }

    }

    public InputStream PrintXLSSubjectList(Subject subject) throws ServiceException {
        try{
            SubjectJournalList sjl = GetSubjectJournalInfo(subject);
            XSSFWorkbook book = new XSSFWorkbook();
            XSSFSheet sheet = book.createSheet(sjl.getSubject().getName());
            XSSFRow row  = sheet.createRow(0);
            sheet.autoSizeColumn(0);
            XSSFCell cell = row.createCell(0);
            cell.setCellValue("Pupil/Date");
            int index=1;
            for(Lesson l : sjl.getLessonList())
            {
                cell = row.createCell(index);
                cell.setCellValue(l.getDate());
                sheet.autoSizeColumn(index);
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

    public InputStream PrintCSVSubjectList(Subject subject) throws ServiceException {
        try{
            CSVFormat csvFileFormat = CSVFormat.DEFAULT.withRecordSeparator(NEW_LINE_SEPARATOR);
            FileWriter fw = new FileWriter("sjl.csv");
            CSVPrinter csvFilePrinter = new CSVPrinter(fw,csvFileFormat);
            SubjectJournalList sjl = GetSubjectJournalInfo(subject);
            List headerList = new ArrayList();
            headerList.add("Pupil/Date");
            for(Lesson l : sjl.getLessonList())
            {
                headerList.add(l.getDate());
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

    public InputStream PrintPDFTeacherWeekSchedule(Teacher teacher) throws ServiceException {
        try{
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
        catch (DocumentException ex) {
            throw new ServiceException(ex);
        }
    }

    public InputStream PrintXLSTeacherWeekSchedule(Teacher teacher) throws ServiceException {
        try{
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

    public InputStream PrintCSVTeacherWeekSchedule(Teacher teacher) throws ServiceException {
        try{
            List<List<ScheduleTeacherLesson>> weekScheduleList = GetTeacherWeekSchedule(teacher);
            CSVFormat csvFileFormat = CSVFormat.DEFAULT.withRecordSeparator(NEW_LINE_SEPARATOR);
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
            List<List<ScheduleTeacherLesson>> resultList = new ArrayList<List<ScheduleTeacherLesson>>();
            for(int i=0;i<7;i++) {
                List<ScheduleTeacherLesson> dayLessonList = new ArrayList<ScheduleTeacherLesson>();
                List<Lesson> lessonList = uof.getLessonDao().GetTeacherDayLessons(teacher.getID(), new Date());
                for(Lesson l : lessonList) {
                    Subject subject = uof.getSubjectDao().Select(l.getID());
                    Class cls = uof.getClassDao().Select(subject.getClassID());
                    ScheduleTeacherLesson stl = new ScheduleTeacherLesson();
                    stl.setLesson(l);
                    stl.setSubject(subject);
                    stl.setCls(cls);
                    dayLessonList.add(stl);
                }
                resultList.add(dayLessonList);
            }
            return resultList;
        }
        catch (DAOException ex){
            throw ex;
        }
    }

    public InputStream PrintPDFPupilWeekSchedule(Pupil pupil) throws ServiceException {
        try{
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
        catch (DocumentException ex) {
            throw new ServiceException(ex);
        }
    }

    public InputStream PrintCSVPupilWeekSchedule(Pupil pupil) throws ServiceException {
        try{
            List<List<SchedulePupilLesson>> weekScheduleList = GetPupilWeekSchedule(pupil);
            CSVFormat csvFileFormat = CSVFormat.DEFAULT.withRecordSeparator(NEW_LINE_SEPARATOR);
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

    public InputStream PrintXLSPupilWeekSchedule(Pupil pupil) throws ServiceException {
        try{
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
            for(int i=0;i<7;i++) {
                List<SchedulePupilLesson> dayLessonList = new ArrayList<SchedulePupilLesson>();
                List<Lesson> lessonList = uof.getLessonDao().GetPupilDayLessons(pupil.getID(),new Date());
                for(Lesson l : lessonList) {
                    Subject subject = uof.getSubjectDao().Select(l.getID());
                    Teacher teacher = uof.getTeacherDao().Select(subject.getTeacherID());
                    SchedulePupilLesson spl = new SchedulePupilLesson();
                    spl.setLesson(l);
                    spl.setSubject(subject);
                    spl.setTeacher(teacher);
                    dayLessonList.add(spl);
                }
                resultList.add(dayLessonList);
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
