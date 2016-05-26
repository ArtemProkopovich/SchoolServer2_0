package Actions.Download;

import Entities.User;
import Services.Interfacies.IPrintService;
import Services.Interfacies.IUserService;
import Services.ServiceFactory;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;

import java.io.InputStream;
import java.util.Map;

/**
 * Created by Артем on 26.05.2016.
 */
public class DownloadJournalList extends ActionSupport {
    private String docType;
    private int subjectID;
    private InputStream fileStream;
    private String fileName;
    private IPrintService printService = ServiceFactory.getPrintService();

    public String execute() throws Exception {
        try {
            if (docType.equals("pdf")) {
                fileStream = printService.PrintPDFSubjectList(subjectID);
                fileName = "SubjectJournal" + subjectID + ".pdf";
                return SUCCESS;
            }
            if (docType.equals("xls")) {
                fileStream = printService.PrintXLSSubjectList(subjectID);
                fileName = "SubjectJournal" + subjectID + ".xlsx";
                return SUCCESS;
            }
            if (docType.equals("csv")) {
                fileStream = printService.PrintCSVSubjectList(subjectID);
                fileName = "SubjectJournal" + subjectID + ".csv";
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

    public int getSubjectID() {
        return subjectID;
    }

    public void setSubjectID(int subjectID) {
        this.subjectID = subjectID;
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
