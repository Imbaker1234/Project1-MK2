package com.POJO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


public @Data
@NoArgsConstructor
@AllArgsConstructor
class Principal {
	
	private String id;
	private String username;
	private String role;
<<<<<<< HEAD
<<<<<<< HEAD

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((role == null) ? 0 : role.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Principal other = (Principal) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (role == null) {
			if (other.role != null)
				return false;
		} else if (!role.equals(other.role))
			return false;
		if (username == null) {
            return other.username == null;
        } else return username.equals(other.username);
    }

	@Override
	public String toString() {
		return "Principal [id=" + id + ", username=" + username + ", role=" + role + "]";
	}
=======
>>>>>>> 3d15da6506159deffc83d216b357add4cd1450ae
=======
>>>>>>> 3d15da6506159deffc83d216b357add4cd1450ae

}
