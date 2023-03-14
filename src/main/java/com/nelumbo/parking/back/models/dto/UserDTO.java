package com.nelumbo.parking.back.models.dto;

import com.nelumbo.parking.back.models.entities.Role;

public record UserDTO (String name, String email, Role role){

}
