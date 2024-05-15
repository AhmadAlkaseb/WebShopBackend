package dtos;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HotelDTO {
    private Integer id;
    private String name;
    private String address;
    private List<Room> rooms;
}