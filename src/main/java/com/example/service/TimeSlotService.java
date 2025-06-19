import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TimeSlotService {
    public List<TimeSlot> getFreeTimeSlotsByTeacherId(int teacherId, LocalDateTime startDate, LocalDateTime endDate, int slotDuration) {
        List<Appointment> appointments = new AppointmentService().getAppointmentsByTeacherId(teacherId);

        // 按时间排序预约记录
        appointments.sort((a1, a2) -> a1.getAppointmentDate().compareTo(a2.getAppointmentDate()));

        List<TimeSlot> freeTimeSlots = new ArrayList<>();

        LocalDateTime current = startDate;
        while (current.isBefore(endDate)) {
            boolean isFree = true;
            for (Appointment appointment : appointments) {
                LocalDateTime appointmentStart = appointment.getAppointmentDate();
                LocalDateTime appointmentEnd = appointment.getAppointmentDate().plusMinutes(appointment.getDuration());

                if (current.isBefore(appointmentEnd) && current.plusMinutes(slotDuration).isAfter(appointmentStart)) {
                    current = appointmentEnd;
                    isFree = false;
                    break;
                }
            }

            if (isFree) {
                freeTimeSlots.add(new TimeSlot(current, current.plusMinutes(slotDuration)));
                current = current.plusMinutes(slotDuration);
            } else {
                current = current.plusMinutes(1); // 移动到下一个时间点
            }
        }

        return freeTimeSlots;
    }
}
