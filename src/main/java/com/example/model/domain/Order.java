@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private Long orderId;
    private String studentId;
    private String teacherId;
    private Date orderTime;
    private Date startTime;
    private Date endTime;
    private String status;
    private BigDecimal amount;
}
