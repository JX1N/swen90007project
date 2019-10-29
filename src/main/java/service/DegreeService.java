package service;

import domain.Degree;
import domain.Subject;

import java.util.Iterator;
import java.util.List;

public class DegreeService {
    private static DegreeService instance;


    private DegreeService() {
    }

    public static DegreeService getInstance() {
        if (instance == null) {
            synchronized (DegreeService.class) {
                if (instance == null) {
                    instance = new DegreeService();
                }
            }
        }
        return instance;
    }

    public List<Degree> getDegreeList() {
        return Degree.getDegreeList();
    }

    public int deleteDegrees(int[] list) {
        return Degree.deleteDegrees(list);
    }

    public int addDegree(Degree degree) {
        return degree.addDegree();
    }

    public int addSubjectToDegree(int[] subjectIds, int degreeId) {
        return Degree.addSubjectToDegree(subjectIds, degreeId);
    }

    public List<Subject> getSubjectListInDegree(String id) {
        int degreeId = Integer.valueOf(id);
        return Degree.getSubjectListInDegree(degreeId);
    }

    public List<Subject> getSubjectListExclude(String id) {
        List<Subject> all = Subject.getAllSubjects();
        List<Subject> degree = getSubjectListInDegree(id);
        Iterator<Subject> iterator = all.iterator();

        while (iterator.hasNext()) {
            Subject sub = iterator.next();
            for (Subject degreeSub : degree) {
                if (sub.getId() == degreeSub.getId()) {
                    iterator.remove();
                }
            }
        }
        return all;
    }

    public int updateDegree(Degree degree) {
        return degree.updateDegree();
    }

    public int deleteSubjectInDegree(String id, int[] subjectIds) {
        return Degree.deleteSubjectInDegree(Integer.valueOf(id), subjectIds);
    }
}
