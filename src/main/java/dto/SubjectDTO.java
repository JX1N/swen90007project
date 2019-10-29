package dto;

public class SubjectDTO {
    private int id;
    private String subjectCode;
    private String subjectName;
    private String subjectDesc;

    public SubjectDTO(int id, String subjectCode, String subjectName, String subjectDesc) {
        this.id = id;
        this.subjectCode = subjectCode;
        this.subjectName = subjectName;
        this.subjectDesc = subjectDesc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getSubjectDesc() {
        return subjectDesc;
    }

    public void setSubjectDesc(String subjectDesc) {
        this.subjectDesc = subjectDesc;
    }
}
