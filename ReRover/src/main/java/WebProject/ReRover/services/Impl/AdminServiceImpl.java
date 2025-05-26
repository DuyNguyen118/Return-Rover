package WebProject.ReRover.services.Impl;

import WebProject.ReRover.model.Admin;
import WebProject.ReRover.repository.AdminRepository;
import WebProject.ReRover.services.AdminService;

public class AdminServiceImpl implements AdminService {
    private AdminRepository adminRepository;

    public AdminServiceImpl(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Override
    public Admin getAdminById(int id) {
        return adminRepository.findById((long) id).orElse(null);
    }

    @Override
    public Admin saveAdmin(Admin admin) {
        return adminRepository.save(admin);
    }

    @Override
    public void deleteAdmin(int id) {
        adminRepository.deleteById((long) id);
    }
}