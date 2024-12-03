package integradora.SIGI.usuarios.control;

import integradora.SIGI.usuarios.model.Rol;
import integradora.SIGI.usuarios.model.Usuario;
import integradora.SIGI.usuarios.model.UsuarioDTO;
import integradora.SIGI.usuarios.model.UsuarioRepository;
import integradora.SIGI.utils.EmailSender;
import integradora.SIGI.utils.Message;
import integradora.SIGI.utils.TypesResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Transactional
@Service
public class UsuarioService {

    private static final Logger logger = LoggerFactory.getLogger(UsuarioService.class);

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    private final EmailSender emailSender;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder, EmailSender emailSender) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailSender = emailSender;
    }

    @Transactional(readOnly = true)
    public Optional<Usuario> findById(Long id) {
        return usuarioRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Message> findAll() {
        logger.info("Iniciando búsqueda de todos los usuarios.");
        List<Usuario> usuarios = usuarioRepository.findAll();
        logger.info("Se encontraron {} usuarios.", usuarios.size());
        return new ResponseEntity<>(new Message(usuarios, "Listado de usuarios", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<Object> saveUsuarios(UsuarioDTO dto) {
        dto.setName(dto.getName().toLowerCase().trim());

        if (dto.getName().length() > 30) {
            return new ResponseEntity<>(new Message("El nombre del Usuario no puede tener más de 30 caracteres", TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        }

        if (dto.getLastname().length() > 30) {
            return new ResponseEntity<>(new Message("El apellido del Usuario no puede tener más de 30 caracteres", TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        }

        if (usuarioRepository.findByEmailIgnoreCase(dto.getEmail()).isPresent()) {
            return new ResponseEntity<>(new Message("El email del usuario ya existe", TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        }

        if (dto.getTelephone().length() > 10) {
            return new ResponseEntity<>(new Message("El numero de telefono del Usuario no puede tener más de 10 caracteres", TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        }

        if (dto.getPassword().length() < 8) {
            return new ResponseEntity<>(new Message("La contraseña del Usuario no puede tener menos de 8 caracteres", TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        }

        String encodedPassword = passwordEncoder.encode(dto.getPassword());

        Usuario usuario = new Usuario(dto.getName(), dto.getLastname(), dto.getEmail(), dto.getTelephone(), encodedPassword, Rol.ROLE_CONSULTOR, true);
        usuarioRepository.save(usuario);
        return new ResponseEntity<>(new Message(usuario, "Usuario registrado exitosamente", TypesResponse.SUCCESS), HttpStatus.CREATED);
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<Object> updateUsuarios(UsuarioDTO dto) {
        dto.setName(dto.getName().toLowerCase().trim());

        if (dto.getName().length() > 30) {
            return new ResponseEntity<>(new Message("El nombre del Usuario no puede tener más de 30 caracteres", TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        }

        if (dto.getLastname().length() > 30) {
            return new ResponseEntity<>(new Message("El apellido del Usuario no puede tener más de 30 caracteres", TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        }

        Optional<Usuario> usuarioOpt = usuarioRepository.findById(dto.getId());
        if (!usuarioOpt.isPresent()) {
            return new ResponseEntity<>(new Message("Usuario no encontrado", TypesResponse.WARNING), HttpStatus.NOT_FOUND);
        }

        if (usuarioRepository.findByEmailIgnoreCaseAndIdNot(dto.getEmail(), dto.getId()).isPresent()) {
            return new ResponseEntity<>(new Message("El email del usuario ya existe", TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        }

        if (dto.getTelephone().length() > 10) {
            return new ResponseEntity<>(new Message("El número de teléfono del Usuario no puede tener más de 10 caracteres", TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        }

        if (dto.getPassword().length() < 8) {
            return new ResponseEntity<>(new Message("La contraseña del Usuario no puede tener menos de 8 caracteres", TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        }

        Usuario usuario = usuarioOpt.get();
        usuario.setName(dto.getName());
        usuario.setLastname(dto.getLastname());
        usuario.setEmail(dto.getEmail());
        usuario.setTelephone(dto.getTelephone());
        usuario.setPassword(dto.getPassword());
        usuarioRepository.save(usuario);

        return new ResponseEntity<>(new Message(usuario, "Usuario actualizado exitosamente", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<Object> changeStatusUsuario(UsuarioDTO dto) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(dto.getId());
        if (!usuarioOpt.isPresent()) {
            return new ResponseEntity<>(new Message("Usuario no encontrado", TypesResponse.WARNING), HttpStatus.NOT_FOUND);
        }

        Usuario usuario = usuarioOpt.get();
        usuario.setStatus(!usuario.isStatus());
        usuarioRepository.save(usuario);

        String estado = usuario.isStatus() ? "habilitado" : "deshabilitado";
        return new ResponseEntity<>(new Message(usuario, "Usuario " + estado + " exitosamente", TypesResponse.SUCCESS), HttpStatus.OK);
    }


    //Recuperacion de contra
    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<Object> sendEmail(UsuarioDTO dto) {
        Optional<Usuario> optional = usuarioRepository.findFirstByEmail(dto.getEmail());
        if(!optional.isPresent()){
            return new ResponseEntity<>(new Message("Usuario no encontrado", TypesResponse.WARNING), HttpStatus.NOT_FOUND);
        }

        Random random = new Random();
        StringBuilder numberString = new StringBuilder();

        for (int i = 0; i < 5; i++) {
            int digit = random.nextInt(10);
            numberString.append(digit);
        }

        Usuario user = optional.get();
        user.setCode(numberString.toString());
        user = usuarioRepository.saveAndFlush(user);
        if(user == null){
            return new ResponseEntity<>(new Message("Código no registrado", TypesResponse.ERROR), HttpStatus.BAD_REQUEST);
        }

        emailSender.sendSimpleMessage(user.getEmail(),
                "Sistema Prueba | Solicitud de restablecimiento de contraseña",
                "<!DOCTYPE html>\n" +
                        "<html xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\" lang=\"en\">\n" +
                        "\n" +
                        "<head>\n" +
                        "\t<title></title>\n" +
                        "\t<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\n" +
                        "\t<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"><!--[if mso]><xml><o:OfficeDocumentSettings><o:PixelsPerInch>96</o:PixelsPerInch><o:AllowPNG/></o:OfficeDocumentSettings></xml><![endif]--><!--[if !mso]><!-->\n" +
                        "\t<link href=\"https://fonts.googleapis.com/css?family=Cabin\" rel=\"stylesheet\" type=\"text/css\"><!--<![endif]-->\n" +
                        "\t<style>\n" +
                        "\t\t* {\n" +
                        "\t\t\tbox-sizing: border-box;\n" +
                        "\t\t}\n" +
                        "\n" +
                        "\t\tbody {\n" +
                        "\t\t\tmargin: 0;\n" +
                        "\t\t\tpadding: 0;\n" +
                        "\t\t}\n" +
                        "\n" +
                        "\t\ta[x-apple-data-detectors] {\n" +
                        "\t\t\tcolor: inherit !important;\n" +
                        "\t\t\ttext-decoration: inherit !important;\n" +
                        "\t\t}\n" +
                        "\n" +
                        "\t\t#MessageViewBody a {\n" +
                        "\t\t\tcolor: inherit;\n" +
                        "\t\t\ttext-decoration: none;\n" +
                        "\t\t}\n" +
                        "\n" +
                        "\t\tp {\n" +
                        "\t\t\tline-height: inherit\n" +
                        "\t\t}\n" +
                        "\n" +
                        "\t\t.desktop_hide,\n" +
                        "\t\t.desktop_hide table {\n" +
                        "\t\t\tmso-hide: all;\n" +
                        "\t\t\tdisplay: none;\n" +
                        "\t\t\tmax-height: 0px;\n" +
                        "\t\t\toverflow: hidden;\n" +
                        "\t\t}\n" +
                        "\n" +
                        "\t\t.image_block img+div {\n" +
                        "\t\t\tdisplay: none;\n" +
                        "\t\t}\n" +
                        "\n" +
                        "\t\tsup,\n" +
                        "\t\tsub {\n" +
                        "\t\t\tfont-size: 75%;\n" +
                        "\t\t\tline-height: 0;\n" +
                        "\t\t}\n" +
                        "\n" +
                        "\t\t@media (max-width:670px) {\n" +
                        "\t\t\t.image_block div.fullWidth {\n" +
                        "\t\t\t\tmax-width: 100% !important;\n" +
                        "\t\t\t}\n" +
                        "\n" +
                        "\t\t\t.mobile_hide {\n" +
                        "\t\t\t\tdisplay: none;\n" +
                        "\t\t\t}\n" +
                        "\n" +
                        "\t\t\t.row-content {\n" +
                        "\t\t\t\twidth: 100% !important;\n" +
                        "\t\t\t}\n" +
                        "\n" +
                        "\t\t\t.stack .column {\n" +
                        "\t\t\t\twidth: 100%;\n" +
                        "\t\t\t\tdisplay: block;\n" +
                        "\t\t\t}\n" +
                        "\n" +
                        "\t\t\t.mobile_hide {\n" +
                        "\t\t\t\tmin-height: 0;\n" +
                        "\t\t\t\tmax-height: 0;\n" +
                        "\t\t\t\tmax-width: 0;\n" +
                        "\t\t\t\toverflow: hidden;\n" +
                        "\t\t\t\tfont-size: 0px;\n" +
                        "\t\t\t}\n" +
                        "\n" +
                        "\t\t\t.desktop_hide,\n" +
                        "\t\t\t.desktop_hide table {\n" +
                        "\t\t\t\tdisplay: table !important;\n" +
                        "\t\t\t\tmax-height: none !important;\n" +
                        "\t\t\t}\n" +
                        "\t\t}\n" +
                        "\t</style><!--[if mso ]><style>sup, sub { font-size: 100% !important; } sup { mso-text-raise:10% } sub { mso-text-raise:-10% }</style> <![endif]-->\n" +
                        "</head>\n" +
                        "\n" +
                        "<body class=\"body\" style=\"background-color: #000000; margin: 0; padding: 0; -webkit-text-size-adjust: none; text-size-adjust: none;\">\n" +
                        "\t<table class=\"nl-container\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; background-color: #000000;\">\n" +
                        "\t\t<tbody>\n" +
                        "\t\t\t<tr>\n" +
                        "\t\t\t\t<td>\n" +
                        "\t\t\t\t\t<table class=\"row row-1\" align=\"center\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; background-color: #f3e6f8;\">\n" +
                        "\t\t\t\t\t\t<tbody>\n" +
                        "\t\t\t\t\t\t\t<tr>\n" +
                        "\t\t\t\t\t\t\t\t<td>\n" +
                        "\t\t\t\t\t\t\t\t\t<table class=\"row-content stack\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; background-color: #ffffff; background-image: url('https://d1oco4z2z1fhwp.cloudfront.net/templates/default/2971/ResetPassword_BG_2.png'); background-position: center top; background-repeat: no-repeat; color: #000000; width: 650px; margin: 0 auto;\" width=\"650\">\n" +
                        "\t\t\t\t\t\t\t\t\t\t<tbody>\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"column column-1\" width=\"100%\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; font-weight: 400; text-align: left; padding-top: 45px; vertical-align: top; border-top: 0px; border-right: 0px; border-bottom: 0px; border-left: 0px;\">\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t\t<table class=\"divider_block block-1\" width=\"100%\" border=\"0\" cellpadding=\"20\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\">\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"pad\">\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"alignment\" align=\"center\">\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" width=\"100%\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\">\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"divider_inner\" style=\"font-size: 1px; line-height: 1px; border-top: 0px solid #BBBBBB;\"><span style=\"word-break: break-word;\">&#8202;</span></td>\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</table>\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</div>\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t\t</table>\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t\t<table class=\"image_block block-2\" width=\"100%\" border=\"0\" cellpadding=\"20\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\">\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"pad\">\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"alignment\" align=\"center\" style=\"line-height:10px\">\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"fullWidth\" style=\"max-width: 357.5px;\"><img src=\"https://d1oco4z2z1fhwp.cloudfront.net/templates/default/2971/lock5.png\" style=\"display: block; height: auto; border: 0; width: 100%;\" width=\"357.5\" alt=\"Forgot your password?\" title=\"Forgot your password?\" height=\"auto\"></div>\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</div>\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t\t</table>\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t\t<table class=\"heading_block block-3\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\">\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"pad\" style=\"padding-top:35px;text-align:center;width:100%;\">\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<h1 style=\"margin: 0; color: #8412c0; direction: ltr; font-family: 'Cabin', Arial, 'Helvetica Neue', Helvetica, sans-serif; font-size: 28px; font-weight: 400; letter-spacing: normal; line-height: 120%; text-align: center; margin-top: 0; margin-bottom: 0; mso-line-height-alt: 33.6px;\"><strong>¿Olvidaste tu contraseña? Este es tú código:</strong></h1>\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t\t</table>\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t\t<table class=\"paragraph_block block-4\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; word-break: break-word;\">\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"pad\" style=\"padding-left:45px;padding-right:45px;padding-top:10px;\">\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<div style=\"color:#393d47;font-family:'Cabin',Arial,'Helvetica Neue',Helvetica,sans-serif;font-size:36px;line-height:200%;text-align:center;mso-line-height-alt:72px;\">\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<p style=\"margin: 0; word-break: break-word;\"><span style=\"word-break: break-word; color: #aa67cf;\">"+user.getCode()+"</span></p>\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</div>\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t\t</table>\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t\t<table class=\"paragraph_block block-5\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; word-break: break-word;\">\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"pad\" style=\"padding-bottom:20px;padding-left:10px;padding-right:10px;padding-top:10px;\">\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<div style=\"color:#8412c0;font-family:Arial, Helvetica Neue, Helvetica, sans-serif;font-size:14px;line-height:120%;text-align:center;mso-line-height-alt:16.8px;\">\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<p style=\"margin: 0; word-break: break-word;\"><span style=\"word-break: break-word; color: #8a3b8f;\">Sistema prueba ©</span></p>\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</div>\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t\t</table>\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                        "\t\t\t\t\t\t\t\t\t\t</tbody>\n" +
                        "\t\t\t\t\t\t\t\t\t</table>\n" +
                        "\t\t\t\t\t\t\t\t</td>\n" +
                        "\t\t\t\t\t\t\t</tr>\n" +
                        "\t\t\t\t\t\t</tbody>\n" +
                        "\t\t\t\t\t</table>\n" +
                        "\t\t\t\t</td>\n" +
                        "\t\t\t</tr>\n" +
                        "\t\t</tbody>\n" +
                        "\t</table>" +
                        "</body>\n" +
                        "\n" +
                        "</html>");

        return new ResponseEntity<>(new Message("Correo enviado", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Object> verifyCode(UsuarioDTO dto) {
        Optional<Usuario> optional = usuarioRepository.findFirstByEmailAndCode(dto.getEmail(),dto.getCode());
        if(!optional.isPresent()){
            return new ResponseEntity<>(new Message("No se pudo verificar", TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(new Message("Verificado", TypesResponse.SUCCESS), HttpStatus.OK);
    }

}
