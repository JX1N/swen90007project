package domain;

import dto.SubjectDTO;
import mapper.SubjectMapper;

import java.util.List;

public class Subject {
    private int id;
    private String subjectCode;
    private String subjectName;
    private String subjectDesc;
    private static SubjectMapper subjectMapper;

    static {
        subjectMapper = SubjectMapper.getInstance();
    }

    public Subject(int id, String subjectCode, String subjectName, String subjectDesc) {
        this.id = id;
        this.subjectCode = subjectCode;
        this.subjectName = subjectName;
        this.subjectDesc = subjectDesc;
    }

    public SubjectDTO transformToDTO() {
        return new SubjectDTO(this.id, this.subjectCode, this.subjectName, this.subjectDesc);
    }

    public static List<Subject> getAllSubjects() {
        return subjectMapper.getAllSubjects();
    }

    public int addSubject() {
        return subjectMapper.addSubject(this);
    }

    public int updateSubject() {
        return subjectMapper.updateSubject(this);
    }

    public static int deleteSubject(int[] ids) {
        return subjectMapper.deleteSubject(ids);
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
