package com.bezkoder.springjwt.controllers;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import com.bezkoder.springjwt.Service.CloudinaryService;
import com.bezkoder.springjwt.Service.UserService;
import com.bezkoder.springjwt.models.*;
import com.bezkoder.springjwt.repository.EmployeeRepo;
import com.bezkoder.springjwt.tfa.MultiFactorAuthentificationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.bezkoder.springjwt.payload.request.LoginRequest;
import com.bezkoder.springjwt.payload.request.SignupRequest;
import com.bezkoder.springjwt.payload.response.JwtResponse;
import com.bezkoder.springjwt.payload.response.MessageResponse;
import com.bezkoder.springjwt.repository.RoleRepository;
import com.bezkoder.springjwt.repository.UserRepository;
import com.bezkoder.springjwt.security.jwt.JwtUtils;
import com.bezkoder.springjwt.security.services.UserDetailsImpl;

//@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
  private static final int MAX_LOGIN_ATTEMPTS = 5;
  private Map<String, Integer> failedLoginAttempts = new HashMap<>();
  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  UserRepository userRepository;
  @Autowired
  RoleRepository roleRepository;
  @Autowired
  EmployeeRepo employeeRepository;


  @Autowired
  PasswordEncoder encoder;

  @Autowired
  JwtUtils jwtUtils;

  @Autowired
  private CloudinaryService cloudinaryService;
  @Autowired
  private MultiFactorAuthentificationService mfaService;
  UserService userService;


 /* @PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = jwtUtils.generateJwtToken(authentication);

    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
    List<String> roles = userDetails.getAuthorities().stream()
        .map(item -> item.getAuthority())
        .collect(Collectors.toList());

    return ResponseEntity.ok(new JwtResponse(jwt,
                         userDetails.getId(),
                         userDetails.getUsername(),
                         userDetails.getEmail(),
                         roles));
  }*/

  /*@PostMapping("/signup")
  public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
    if (userRepository.existsByUsername(signUpRequest.getUsername())) {
      return ResponseEntity
          .badRequest()
          .body(new MessageResponse("Error: Username is already taken!"));
    }

    if (userRepository.existsByEmail(signUpRequest.getEmail())) {
      return ResponseEntity
          .badRequest()
          .body(new MessageResponse("Error: Email is already in use!"));
    }

    // Create new user's account
    User user = new User(signUpRequest.getUsername(),
               signUpRequest.getEmail(),
               encoder.encode(signUpRequest.getPassword()));

    Set<String> strRoles = signUpRequest.getRole();
    Set<Role> roles = new HashSet<>();

    if (strRoles == null) {
      Role userRole = roleRepository.findByName(ERole.ROLE_USER)
          .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
      roles.add(userRole);
    } else {
      strRoles.forEach(role -> {
        switch (role) {
        case "admin":
          Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
          roles.add(adminRole);

          break;
        case "mod":
          Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
          roles.add(modRole);

          break;
        default:
          Role userRole = roleRepository.findByName(ERole.ROLE_USER)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
          roles.add(userRole);
        }
      });
    }

    user.setRoles(roles);
    userRepository.save(user);

    return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
  }*/

 /* @PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
    String username = loginRequest.getUsername();
    int failedAttempts = failedLoginAttempts.getOrDefault(username, 0);

    if (failedAttempts >= MAX_LOGIN_ATTEMPTS) {
      User user = userRepository.findByUsername(username)
              .orElseThrow(() -> new RuntimeException("User not found"));
      user.setStatus(AccountStatus.BANNED);
      userRepository.save(user);
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse("Account is banned"));
    }

    try {
      Authentication authentication = authenticationManager.authenticate(
              new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

      SecurityContextHolder.getContext().setAuthentication(authentication);
      String jwt = jwtUtils.generateJwtToken(authentication);

      UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
      List<String> roles = userDetails.getAuthorities().stream()
              .map(item -> item.getAuthority())
              .collect(Collectors.toList());

      failedLoginAttempts.remove(username); // Réinitialiser le nombre de tentatives infructueuses

      return ResponseEntity.ok(new JwtResponse(jwt,
              userDetails.getId(),
              userDetails.getUsername(),
              userDetails.getEmail(),
              roles));
    } catch (BadCredentialsException e) {
      // Incrémenter le nombre de tentatives infructueuses
      failedLoginAttempts.put(username, failedAttempts + 1);
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MessageResponse("Invalid username or password"));
    }


  }*/



  /*@PostMapping(value = "/signup", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<?> registerUser(@Valid @ModelAttribute SignupRequest signUpRequest) {

    if (userRepository.existsByUsername(signUpRequest.getUsername())) {
      return ResponseEntity
              .badRequest()
              .body(new MessageResponse("Error: Username is already taken!"));
    }

    if (userRepository.existsByEmail(signUpRequest.getEmail())) {
      return ResponseEntity
              .badRequest()
              .body(new MessageResponse("Error: Email is already in use!"));
    }

    // Create new user's account
    User user = new User(signUpRequest.getUsername(),
            signUpRequest.getEmail(),
            encoder.encode(signUpRequest.getPassword()));

    Set<String> strRoles = signUpRequest.getRole();
    Set<Role> roles = new HashSet<>();

    if (strRoles == null) {
      Role userRole = roleRepository.findByName(ERole.ROLE_USER)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
      roles.add(userRole);
    } else {
      strRoles.forEach(role -> {
        switch (role) {
          case "admin":
            Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(adminRole);

            break;
          case "mod":
            Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(modRole);

            break;
          default:
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        }
      });
    }

    user.setRoles(roles);

    // Ajout de l'image de profil
    try {
      String imageUrl = cloudinaryService.upload(signUpRequest.getImage());
      user.setImage(imageUrl);
    } catch (IOException e) {
      return ResponseEntity
              .badRequest()
              .body(new MessageResponse("Error: Failed to upload image!"));

    }

    userRepository.save(user);
    Map<String, Object> response = new HashMap<>();
    response.put("message", "User registered successfully!");
    response.put("imageUrl", user.getImage());

    return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
  }*/

  @PostMapping("/signout")
  public ResponseEntity<?> signoutUser(HttpServletRequest request) {
    String authorizationHeader = request.getHeader("Authorization");
    if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
      String jwt = authorizationHeader.substring(7);

    }
    return ResponseEntity.ok(new MessageResponse("User signed out successfully!"));
  }

  @PutMapping("/users/{userId}/unlock")
  @PreAuthorize("hasRole('ADMIN')")

  public ResponseEntity<?> unlockAccount(@PathVariable Long userId) {
    // Récupérer l'utilisateur à partir de son ID
    Optional<User> optionalUser = userRepository.findById(userId);
    if (optionalUser.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    // Débloquer le compte en mettant à jour le statut
    User user = optionalUser.get();
    user.setStatus(AccountStatus.LOCKED); // Supposons que vous avez un enum AccountStatus avec ACTIVE, BANNED, etc.
    userRepository.save(user);

    return ResponseEntity.ok(new MessageResponse("Le compte a été débloqué avec succès"));
  }


  /* @PostMapping("/signin")
   public ResponseEntity<?> authenticateUser(HttpServletRequest request, @Valid @RequestBody LoginRequest loginRequest) {
     String username = loginRequest.getUsername();
     int failedAttempts = failedLoginAttempts.getOrDefault(username, 0);

     if (failedAttempts >= MAX_LOGIN_ATTEMPTS) {
       // Gérer le cas de l'utilisateur banni (comme précédemment)
       return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse("Le compte est banni"));
     }

     try {
       Authentication authentication = authenticationManager.authenticate(
               new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

       SecurityContextHolder.getContext().setAuthentication(authentication);

       UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

       Optional<User> userOptional = userRepository.findByUsername(username);
       if (userOptional.isPresent() && userOptional.get().isMfaEnabled()) {
         // MFA activé, vérifier le code OTP
         String otp = request.getHeader("OTP"); // Supposons que OTP est envoyé dans un en-tête

         if (otp == null || !mfaService.isOtpValid(userOptional.get().getTotpSecret(), otp)) {
           return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MessageResponse("Code OTP invalide"));
         }
       }

       String jwt = jwtUtils.generateJwtToken(authentication);

       List<String> roles = userDetails.getAuthorities().stream()
               .map(item -> item.getAuthority())
               .collect(Collectors.toList());

       failedLoginAttempts.remove(username); // Réinitialiser les tentatives infructueuses

       return ResponseEntity.ok(new JwtResponse(jwt,
               userDetails.getId(),
               userDetails.getUsername(),
               userDetails.getEmail(),
               roles));
     } catch (BadCredentialsException e) {
       // Incrémenter les tentatives infructueuses
       failedLoginAttempts.put(username, failedAttempts + 1);
       return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MessageResponse("Nom d'utilisateur ou mot de passe incorrect"));
     }
   }*/
 /* @PostMapping(value = "/signup", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<?> registerUser(@Valid @ModelAttribute SignupRequest signUpRequest) {
    // Vérifiez si le nom d'utilisateur est déjà pris
    if (userRepository.existsByUsername(signUpRequest.getUsername())) {
      return ResponseEntity
              .badRequest()
              .body(new MessageResponse("Error: Username is already taken!"));
    }

    // Vérifiez si l'e-mail est déjà utilisé
    if (userRepository.existsByEmail(signUpRequest.getEmail())) {
      return ResponseEntity
              .badRequest()
              .body(new MessageResponse("Error: Email is already in use!"));
    }

    // Créez le compte de l'utilisateur
    User user = new User(signUpRequest.getUsername(),
            signUpRequest.getEmail(),
            encoder.encode(signUpRequest.getPassword()));

    // Ajoutez les rôles de l'utilisateur
    Set<String> strRoles = signUpRequest.getRole();
    Set<Role> roles = new HashSet<>();
    if (strRoles == null) {
      Role userRole = roleRepository.findByName(ERole.ROLE_USER)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
      roles.add(userRole);
    } else {
      strRoles.forEach(role -> {
        switch (role) {
          case "admin":
            Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(adminRole);
            break;
          case "mod":
            Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(modRole);
            break;
          default:
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        }
      });
    }
    user.setRoles(roles);

    // Ajout de l'image de profil
    try {
      String imageUrl = cloudinaryService.upload(signUpRequest.getImage());
      user.setImage(imageUrl);
    } catch (IOException e) {
      return ResponseEntity
              .badRequest()
              .body(new MessageResponse("Error: Failed to upload image!"));
    }

    // Enregistrer l'utilisateur dans la base de données
    userRepository.save(user);

    // Générer un secret TOTP unique pour l'utilisateur
    String secret = mfaService.generateNewSecret();

    // Enregistrer le secret TOTP dans la base de données avec l'utilisateur
    user.setTotpSecret(secret);
    userRepository.save(user);

    String qrCodeUri = mfaService.generateQrCodeImageUri(secret);

    Map<String, Object> response = new HashMap<>();
    response.put("message", "User registered successfully! MFA setup required.");
    response.put("qrCodeUri", qrCodeUri);

    return ResponseEntity.ok(response);
  }*/

 /*@PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
    String username = loginRequest.getUsername();
    int failedAttempts = failedLoginAttempts.getOrDefault(username, 0);

    if (failedAttempts >= MAX_LOGIN_ATTEMPTS) {
      User user = userRepository.findByUsername(username)
              .orElseThrow(() -> new RuntimeException("User not found"));
      user.setStatus(AccountStatus.BANNED);
      userRepository.save(user);
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse("Account is banned"));
    }

    try {
      Authentication authentication = authenticationManager.authenticate(
              new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

      // Vérifier si l'authentification MFA est nécessaire pour cet utilisateur
      Optional<User> userOptional = userRepository.findByUsername(username);
      if (userOptional.isPresent() && userOptional.get().isMfaEnabled()) {
        String otp = loginRequest.getTotpSecret(); // Récupérer le code OTP soumis par l'utilisateur
        if (otp == null || otp.isEmpty()) {
          return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MessageResponse("Code OTP is required"));
        }

        // Vérifier la validité du code OTP
        if (!mfaService.isOtpValid(userOptional.get().getTotpSecret(), otp)) {
          return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MessageResponse("Invalid OTP code"));
        }
      }

      // Authentifier l'utilisateur et générer le token JWT
      SecurityContextHolder.getContext().setAuthentication(authentication);
      String jwt = jwtUtils.generateJwtToken(authentication);

      // Extraire les informations de l'utilisateur pour la réponse
      UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
      List<String> roles = userDetails.getAuthorities().stream()
              .map(item -> item.getAuthority())
              .collect(Collectors.toList());

      failedLoginAttempts.remove(username); // Réinitialiser le nombre de tentatives infructueuses

      // Renvoyer la réponse avec le token JWT et les informations de l'utilisateur
      return ResponseEntity.ok(new JwtResponse(jwt,
              userDetails.getId(),
              userDetails.getUsername(),
              userDetails.getEmail(),
              roles));
    } catch (BadCredentialsException e) {
      // Incrémenter le nombre de tentatives infructueuses
      failedLoginAttempts.put(username, failedAttempts + 1);
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MessageResponse("Invalid username or password"));
    }
  }*/


  @PostMapping(value = "/signup", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<?> registerUser(@Valid @ModelAttribute SignupRequest signUpRequest) {
    // Vérifiez si le nom d'utilisateur est déjà pris
    if (userRepository.existsByUsername(signUpRequest.getUsername())) {
      return ResponseEntity
              .badRequest()
              .body(new MessageResponse("Error: Username is already taken!"));
    }

    // Vérifiez si l'e-mail est déjà utilisé
    if (userRepository.existsByEmail(signUpRequest.getEmail())) {
      return ResponseEntity
              .badRequest()
              .body(new MessageResponse("Error: Email is already in use!"));
    }

    // Créez le compte de l'utilisateur
    User user = new User(signUpRequest.getUsername(),
            signUpRequest.getEmail(),
            encoder.encode(signUpRequest.getPassword()));

    // Ajoutez les rôles de l'utilisateur
    Set<String> strRoles = signUpRequest.getRole();
    Set<Role> roles = new HashSet<>();
    if (strRoles == null) {
      Role userRole = roleRepository.findByName(ERole.ROLE_USER)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
      roles.add(userRole);
    } else {
      strRoles.forEach(role -> {
        switch (role) {
          case "admin":
            Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(adminRole);
            break;
          case "productowner":
            Role PownerRole = roleRepository.findByName(ERole.ROLE_PRODUCT_OWNER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(PownerRole);
            break;
          case "Rh":
            Role Rh = roleRepository.findByName(ERole.ROLE_RH)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(Rh);
            break;
          case "mod":
            Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(modRole);
            break;
          case "employee":
            Role employeeRole = roleRepository.findByName(ERole.ROLE_EMPLOYEE)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(employeeRole);

            User savedUser = userRepository.save(user);

// Récupérer l'ID de l'utilisateur nouvellement créé
            Long userId = savedUser.getId();

// Ensuite, créez l'employé et associez l'ID de l'utilisateur
            Employee employee = new Employee();
            employee.setUserId(userId);

// Enregistrez l'employé
            employeeRepository.save(employee);
            break;
          default:
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        }
      });
    }
    user.setRoles(roles);

    // Ajout de l'image de profil
    try {
      Map<String, Object> uploadResult = cloudinaryService.upload(signUpRequest.getImage());
      String imageUrl = (String) uploadResult.get("url");
      user.setImage(imageUrl);
    } catch (IOException e) {
      return ResponseEntity
              .badRequest()
              .body(new MessageResponse("Error: Failed to upload image!"));
    }

    // Générer un secret TOTP unique pour l'utilisateur
    String secret = mfaService.generateNewSecret();
    user.setTotpSecret(secret);

    // Activer l'authentification MFA dès le signup
    user.setMfaEnabled(true);

    // Enregistrement de l'utilisateur dans la base de données
    userRepository.save(user);

    String qrCodeUri = mfaService.generateQrCodeImageUri(secret);

    Map<String, Object> response = new HashMap<>();
    response.put("message", "User registered successfully! MFA setup completed.");
    response.put("qrCodeUri", qrCodeUri);

    return ResponseEntity.ok(response);
  }
  /*@PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
    String username = loginRequest.getUsername();

    try {
      // Récupérer l'utilisateur depuis la base de données
      Optional<User> userOptional = userRepository.findByUsername(username);
      if (!userOptional.isPresent()) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MessageResponse("User not found"));
      }
      User user = userOptional.get();

      // Vérifier si l'utilisateur a activé MFA
      if (user.isMfaEnabled()) {
        // Récupérer la clé secrète associée à l'utilisateur
        String secret = user.getTotpSecret();

        // Vérifier le code OTP fourni par l'utilisateur
        String otp = loginRequest.getTotpSecret();
        if (otp == null || otp.isEmpty()) {
          return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MessageResponse("Code OTP is required"));
        }
        if (!mfaService.isOtpValid(secret, otp)) {
          return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MessageResponse("Invalid OTP code"));
        }
      }

      // Si l'authentification réussit, générer le token JWT
      Authentication authentication = authenticationManager.authenticate(
              new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
      SecurityContextHolder.getContext().setAuthentication(authentication);
      String jwt = jwtUtils.generateJwtToken(authentication);

      // Extraire les informations de l'utilisateur pour la réponse
      UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
      List<String> roles = userDetails.getAuthorities().stream()
              .map(item -> item.getAuthority())
              .collect(Collectors.toList());

      return ResponseEntity.ok(new JwtResponse(jwt,
              userDetails.getId(),
              userDetails.getUsername(),
              userDetails.getEmail(),
              roles));
    } catch (BadCredentialsException e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MessageResponse("Invalid username or password"));
    }
  }*/


  @PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
    String username = loginRequest.getUsername();
    int failedAttempts = failedLoginAttempts.getOrDefault(username, 0);

    if (failedAttempts >= MAX_LOGIN_ATTEMPTS) {
      User user = userRepository.findByUsername(username)
              .orElseThrow(() -> new RuntimeException("User not found"));
      user.setStatus(AccountStatus.BANNED);
      userRepository.save(user);
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse("Account is banned"));
    }

    try {
      Authentication authentication;

      // Vérifier si l'authentification MFA est nécessaire pour cet utilisateur
      Optional<User> userOptional = userRepository.findByUsername(username);
      if (userOptional.isPresent() && userOptional.get().isMfaEnabled()) {
        String otp = loginRequest.getTotpSecret(); // Récupérer le code OTP soumis par l'utilisateur
        if (otp == null || otp.isEmpty()) {
          return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MessageResponse("Code OTP is required"));
        }

        // Vérifier la validité du code OTP
        if (!mfaService.isOtpValid(userOptional.get().getTotpSecret(), otp)) {
          return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MessageResponse("Invalid OTP code"));
        }
      }

      // Authentifier l'utilisateur et générer le token JWT
      authentication = authenticationManager.authenticate(
              new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

      SecurityContextHolder.getContext().setAuthentication(authentication);
      String jwt = jwtUtils.generateJwtToken(authentication);

      // Réinitialiser le nombre de tentatives infructueuses
      failedLoginAttempts.remove(username);

      // Extraire les informations de l'utilisateur pour la réponse
      UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
      List<String> roles = userDetails.getAuthorities().stream()
              .map(item -> item.getAuthority())
              .collect(Collectors.toList());

      // Renvoyer la réponse avec le token JWT et les informations de l'utilisateur
      return ResponseEntity.ok(new JwtResponse(jwt,
              userDetails.getId(),
              userDetails.getUsername(),
              userDetails.getEmail(),
              roles));
    } catch (BadCredentialsException e) {
      // Incrémenter le nombre de tentatives infructueuses
      failedLoginAttempts.put(username, failedAttempts + 1);
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MessageResponse("Invalid username or password"));
    }
  }


  @PutMapping("/activate/{userId}")
  public ResponseEntity<?> activateAccount(@PathVariable Long userId) {
    // Vérifier si l'utilisateur existe dans la base de données
    Optional<User> userOptional = userRepository.findById(userId);
    if (userOptional.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("User not found"));
    }

    User user = userOptional.get();
    if (user.getStatus() == AccountStatus.BANNED) {
      // Activer le compte en mettant à jour le statut
      user.setStatus(AccountStatus.ACTIVE);
      userRepository.save(user);
      return ResponseEntity.ok(new MessageResponse("Account activated successfully"));
    } else {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse("Account is not banned"));
    }


  }

}

