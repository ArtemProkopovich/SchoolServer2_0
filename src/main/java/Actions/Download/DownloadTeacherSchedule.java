package Actions.Download;

import Services.Interfacies.IPrintService;
import Services.ServiceFactory;
import com.opensymphony.xwork2.ActionSupport;

import java.io.InputStream;

/**
 * Created by Артем on 26.05.2016.
 */
public class DownloadTeacherSchedule extends ActionSupport {
    private String docType;
    private int teacherID;
    private InputStream fileStream;
    private String fileName;
    private IPrintService printService = ServiceFactory.getPrintService();

    public String execute() throws Exception {
        try {
            if (docType.equals("pdf")) {
                fileStream = printService.PrintPDFTeacherWeekSchedule(teacherID);
                fileName = "Schedule" + teacherID + ".pdf";
                return SUCCESS;
            }
            if (docType.equals("xls")) {
                fileStream = printService.PrintXLSTeacherWeekSchedule(teacherID);
                fileName = "Schedule" + teacherID + ".xlsx";
                return SUCCESS;
            }
            if (docType.equals("csv")) {
                fileStream = printService.PrintCSVTeacherWeekSchedule(teacherID);
                fileName = "Schedule" + teacherID + ".csv";
                return SUCCESS;
            }
            return ERROR;
        } catch (Exception ex) {
            return ERROR;
        }
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public int getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(int teacherID) {
        this.teacherID = teacherID;
    }

    public InputStream getFileStream() {
        return fileStream;
    }

    public void setFileStream(InputStream fileStream) {
        this.fileStream = fileStream;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
