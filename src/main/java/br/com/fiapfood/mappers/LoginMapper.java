package br.com.fiapfood.mappers;

import org.springframework.stereotype.Component;

import br.com.fiapfood.entities.db.LoginEntity;
import br.com.fiapfood.entities.domain.LoginDomain;
import br.com.fiapfood.entities.record.request.LoginRecordRequest;
import br.com.fiapfood.entities.record.response.LoginRecordResponse;

@Component
public abstract class LoginMapper {
	
	// record -> domain -> entity
	
	// 1 - record -> domain
	public static LoginDomain toLogin(LoginRecordRequest login) {
		return new LoginDomain(null, login.matricula(), login.senha(), null);
	}

	// 2 - domain -> entity
	
	public static LoginEntity toLogin(LoginDomain login) {
		return new LoginEntity(login.getId(), login.getMatricula(), login.getSenha(), null);
	}
	
	// entity -> domain -> record
	
	// 3 - entity -> domain
	
	public static LoginDomain toLogin(LoginEntity login) {
		return new LoginDomain(login.getId(), login.getMatricula(), login.getSenha(), null);
	}
	
	// 4 - domain -> record
	
	public static LoginRecordResponse toLoginRecord(LoginDomain login) {
		return new LoginRecordResponse(login.getMatricula());
	}
}