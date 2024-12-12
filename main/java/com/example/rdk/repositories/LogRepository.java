package com.example.rdk.repositories;

import com.example.rdk.models.Company;
import com.example.rdk.models.Facility;
import com.example.rdk.models.Log;
import com.example.rdk.models.WorkType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface LogRepository extends JpaRepository<Log, Integer> {
    List<Log> findAllByDateAndFacilityAndWorkTypeOrderByIdDesc(Date date, Facility facility, WorkType workType);

    @Query("SELECT distinct date from Log where facility = ?1 and workType = ?2 ORDER BY date DESC ")
    List<Date> findUniqueDate(Facility facility, WorkType workType);
    List<Log> findAllByFacilityAndWorkType(Facility facility, WorkType workType);
    List<Log> findAllByFacilityAndWorkTypeAndDate(Facility facility, WorkType workType, Date date);

   List<Log> findByDateAndTagNumOrderByIdAsc(Date date, String tagNum);
}
