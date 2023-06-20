package com.nkxgen.spring.orm.dao;
import java.util.List;

import com.nkxgen.spring.orm.model.Sprint;

public interface SprintDAO {
    Sprint getSprintById(int sprintId);
    List<Sprint> getAllSprints();
    void createSprint(Sprint sprint);
    void updateSprint(Sprint sprint);
    void deleteSprint(int sprintId);
}
