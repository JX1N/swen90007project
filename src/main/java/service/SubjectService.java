package service;

import mapper.SubjectMapper;
import domain.Subject;

import java.util.List;

public class SubjectService {
    private static SubjectService instance;
    private SubjectMapper subjectMapper;

    private SubjectService() {
        subjectMapper = SubjectMapper.getInstance();
    }

    public static SubjectService getInstance() {
        if (instance == null) {
            synchronized (SubjectService.class) {
                if (instance == null) {
                    instance = new SubjectService();
                }
            }
        }
        return instance;
    }

    public List<Subject> getAllSubjects() {
        return Subject.getAllSubjects();
    }

    public int addSubject(Subject subject) {
        return subject.addSubject();
    }

    public int updateSubject(Subject subject) {
        return subject.updateSubject();
    }

    public int deleteSubject(int[] ids) {
        return Subject.deleteSubject(ids);
    }
}
