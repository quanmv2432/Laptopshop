package com.laptopshop.config;

import com.laptopshop.entities.NguoiDung;
import com.laptopshop.entities.VaiTro;
import com.laptopshop.repository.NguoiDungRepository;
import com.laptopshop.repository.VaiTroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
public class DataSeeder implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	private NguoiDungRepository userRepository;

	@Autowired
	private VaiTroRepository roleRepository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent arg0) {
		// vai trof
		if (roleRepository.findByTenVaiTro("ROLE_ADMIN") == null) {
			roleRepository.save(new VaiTro("ROLE_ADMIN"));
		}

		if (roleRepository.findByTenVaiTro("ROLE_MEMBER") == null) {
			roleRepository.save(new VaiTro("ROLE_MEMBER"));
		}
		
		if (roleRepository.findByTenVaiTro("ROLE_SHIPPER") == null) {
			roleRepository.save(new VaiTro("ROLE_SHIPPER"));
		}

		// Admin account
		if (userRepository.findByEmail("lucbui.admin@gmail.com") == null) {
			NguoiDung admin = new NguoiDung();
			admin.setEmail("lucbui.admin@gmail.com");
			admin.setPassword(passwordEncoder.encode("1302"));
			admin.setHoTen("Bùi Tuấn Lực");
			admin.setSoDienThoai("123456789");
			HashSet<VaiTro> roles = new HashSet<>();
			roles.add(roleRepository.findByTenVaiTro("ROLE_ADMIN"));
			roles.add(roleRepository.findByTenVaiTro("ROLE_MEMBER"));
			admin.setVaiTro(roles);
			userRepository.save(admin);
		}

		// Member account
		if (userRepository.findByEmail("lucbui.member@gmail.com") == null) {
			NguoiDung member = new NguoiDung();
			member.setEmail("lucbui.member@gmail.com");
			member.setPassword(passwordEncoder.encode("1302"));
			member.setHoTen("Bùi Lực");
			HashSet<VaiTro> roles = new HashSet<>();
			roles.add(roleRepository.findByTenVaiTro("ROLE_MEMBER"));
			member.setVaiTro(roles);
			userRepository.save(member);
		}
		
		// Shipper account
		if (userRepository.findByEmail("lucbui.shipper@gmail.com") == null) {
			NguoiDung member = new NguoiDung();
			member.setEmail("lucbui.shipper@gmail.com");
			member.setPassword(passwordEncoder.encode("1302"));
			member.setHoTen("Tuấn Lực");
			HashSet<VaiTro> roles = new HashSet<>();
			roles.add(roleRepository.findByTenVaiTro("ROLE_SHIPPER"));
			member.setVaiTro(roles);
			userRepository.save(member);
		}
	}
}
