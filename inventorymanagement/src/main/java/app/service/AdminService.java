package app.service;

import org.springframework.stereotype.Service;

import app.customexception.BusinessServiceException;
import app.customexception.ConstrainFailedException;
import app.customexception.DuplicateException;
import app.customexception.NotFoundException;
import app.customexception.UserNotFoundException;
import app.model.Admin;
import app.pojo.Login;

@Service
public interface AdminService {

	Admin saveAdmin(Admin admin) throws DuplicateException, BusinessServiceException, ConstrainFailedException;

	//List<Admin> getDetails() throws BusinessServiceException;

	Admin getById(long id)throws BusinessServiceException,NotFoundException;

	Admin updatePersonalDetails(Admin admin, Long id) throws BusinessServiceException, NotFoundException;

	Long getAdminByLoginDetails( Login admin) throws UserNotFoundException, BusinessServiceException;

}
