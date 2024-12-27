/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;
import dal.QuestionDAO;
import model.Question;
import java.util.List;
/**
 *
 * @author Windows 10
 */
public class test {
    public static void main(String[] args) {
        // Tạo đối tượng QuestionDAO
        QuestionDAO questionDAO = new QuestionDAO();
        
        // Nhập chuỗi tìm kiếm (ví dụ: "Phần của đường bộ được sử dụng cho")
        String searchQuery = "Phần của đường bộ được sử dụng cho";
        
        // Gọi phương thức searchQuestions và lấy danh sách câu hỏi tìm được
        List<Question> results = questionDAO.searchQuestions(searchQuery);
        
        // Hiển thị kết quả tìm kiếm
        if (results.isEmpty()) {
            System.out.println("Không tìm thấy câu hỏi nào với từ khóa: " + searchQuery);
        } else {
            System.out.println("Kết quả tìm kiếm cho từ khóa: " + searchQuery);
            for (Question question : results) {
                System.out.println("Question ID: " + question.getQuestionId() + ", Content: " + question.getContent());
            }
        }
    }

}
