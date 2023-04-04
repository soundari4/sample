package app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import app.customexception.BusinessServiceException;
import app.customexception.ConstrainFailedException;
import app.customexception.DuplicateException;
import app.customexception.NotFoundException;
import app.customexception.UserNotFoundException;
import app.model.Admin;
import app.pojo.Login;
import app.repository.AdminRepository;
import app.service.AdminService;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	AdminRepository adminRepository;

	@Override
	public Admin saveAdmin(Admin admin) throws DuplicateException, BusinessServiceException, ConstrainFailedException {
		Admin savedAdmin = new Admin();
		try {
			if (admin.getAge() == 0 )
				throw new ConstrainFailedException();
			savedAdmin = adminRepository.save(admin);
		} catch (ConstrainFailedException e) {
			throw new ConstrainFailedException("Age must be given");
		} catch (Exception e) {
			while (e != null) {
				if (e.getCause().getCause().getMessage().contains("Duplicate entry")
						&& e.getCause().getCause().getMessage().contains("UK_PhoneNumber"))
					throw new DuplicateException("Phone Number is already exists");
				else
					throw new DuplicateException("Username is already exists");
			}
			throw new BusinessServiceException();
		}
		return savedAdmin;

	}

//	@Override
//	public List<Admin> getDetails() throws BusinessServiceException {
//		try {
//			return adminRepository.findAll();
//		} catch (Exception e) {
//			throw new BusinessServiceException();
//		}
//	}

	@Override
	public Admin getById(long id) {
		return adminRepository.findById(id).get();
	}

	@Override
	public Admin updatePersonalDetails(Admin admin, Long id) throws BusinessServiceException, NotFoundException {
	//	try {
			Admin updateAdminDetails = adminRepository.findById(id).get();
			if (updateAdminDetails != null) {
				if (admin.getUsername() == null)
					updateAdminDetails.setUsername(updateAdminDetails.getUsername());
				else
					updateAdminDetails.setUsername(admin.getUsername());
				if (admin.getPassWord() == null)
					updateAdminDetails.setPassWord(updateAdminDetails.getPassWord());
				else
					updateAdminDetails.setPassWord(admin.getPassWord());
				if (admin.getPhoneNumber() == null)
					updateAdminDetails.setPhoneNumber(updateAdminDetails.getPhoneNumber());
				else
					updateAdminDetails.setPhoneNumber(admin.getPhoneNumber());
				System.out.println(updateAdminDetails.getId());
				return adminRepository.save(updateAdminDetails);

			} else
				throw new NotFoundException();
//		} catch (EmptyResultDataAccessException | NotFoundException e) {
//			throw new NotFoundException();
//		} catch (Exception e) {
//			throw new BusinessServiceException();
//		}
	}

	@Override
	public Long getAdminByLoginDetails(Login admin) throws UserNotFoundException, BusinessServiceException {
		try {
			Admin existingAdmin = adminRepository.findByUsernameAndPassword(admin.getUsername(), admin.getPassWord());
			//System.out.println(existingAdmin.getName());
			
			if (existingAdmin == null)
				throw new UserNotFoundException();
			return existingAdmin.getId();
			// else

		} catch (UserNotFoundException e) {
			throw new UserNotFoundException("No Admin is found with this username and password");
		} catch (Exception e) {
			throw new BusinessServiceException();
		}
	}

}
