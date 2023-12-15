package uz.pdp.online;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@Data
public class Student {
    private List<String> subjects;
    private Map<String, String> subjectGrades;
}
