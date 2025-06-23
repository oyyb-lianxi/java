@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private Long id;
    private String studentId;
    private String teacherId;
    private LocalDateTime orderTime;
    private LocalDateTime orderTime;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String status;
    private BigDecimal amount;
    
}
