package dto;

import java.util.List;

public class StudentSubjectDTO {
    long id;
    String username;
    List<SubjectDTO> subjects;
    List<Integer> degreeIds;

    public StudentSubjectDTO(long id, String username, List<SubjectDTO> subjects, List<Integer> degreeId) {
        this.id = id;
        this.username = username;
        this.subjects = subjects;
        this.degreeIds = degreeId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<SubjectDTO> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<SubjectDTO> subjects) {
        this.subjects = subjects;
    }

    public List<Integer> getDegreeId() {
        return degreeIds;
    }

    public void setDegreeId(List<Integer> degreeId) {
        this.degreeIds = degreeId;
    }
}
