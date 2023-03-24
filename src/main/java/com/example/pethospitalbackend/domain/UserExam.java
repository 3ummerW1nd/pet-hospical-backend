package com.example.pethospitalbackend.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_exam")
@IdClass(UserExamId.class)
public class UserExam {
    @Id
    private Integer user_id;
    @Id
    private Integer exam_id;
    private Boolean is_done;
    private Integer history_score;
}

@Getter
@Setter
class UserExamId implements Serializable {
    private Integer user_id;
    private Integer exam_id;

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof UserExamId){
            UserExamId pk=(UserExamId)obj;
            if(this.user_id.equals(pk.user_id)&&this.exam_id.equals(pk.exam_id)){
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
