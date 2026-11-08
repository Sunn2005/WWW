package fit.iuh.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "employee")
public class Employee {
    @Id
    private Integer id;
    private String name;
    private String role;
    private Integer age;
    private Double salary;
    @Field("departmentId")
    private Integer departmentId;
    @Field("positionId")
    private Integer positionId;
    // Không lưu xuống Mongo, chỉ để hiển thị
    @Transient
    private String departmentName;
    @Transient
    private String positionTitle;
}
