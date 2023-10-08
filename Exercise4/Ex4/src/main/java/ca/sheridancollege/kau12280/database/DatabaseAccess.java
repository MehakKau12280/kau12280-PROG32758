package ca.sheridancollege.kau12280.database;

import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import ca.sheridancollege.kau12280.beans.School;
import lombok.Data;

@Repository
@Data
public class DatabaseAccess {
       private ArrayList<School> schoolList = new ArrayList<>();
}
