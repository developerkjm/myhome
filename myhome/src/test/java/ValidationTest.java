import com.godcoder.myhome.model.Board;
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

public class ValidationTest {
    public static void main(String[] args) {
//        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        ValidatorFactory factory = Validation.byDefaultProvider()
                .configure()
                .messageInterpolator(new ParameterMessageInterpolator()) // EL 없이 메시지 처리
                .buildValidatorFactory();
        Validator validator = factory.getValidator();


        Board board = new Board();
        board.setTitle("");

        Set<ConstraintViolation<Board>> violations = validator.validate(board);

        for (ConstraintViolation<Board> violation : violations) {
            System.out.println(violation.getMessage());
        }
    }
}
