package com.example.api_transporte.Controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.api_transporte.DTO.EmpresaTransporteDTO.getTransportistaDTO;
import com.example.api_transporte.DTO.EmpresaTransporteDTO.saveTransportista;
import com.example.api_transporte.DTO.EmpresaTransporteDTO.TransportistaMapper;
import com.example.api_transporte.Model.Transportistas;
import com.example.api_transporte.Service.TransportistasService;

@RestController
@RequestMapping("/api/TransportistasApi")
public class TransportistasController {

    // inyeccion del servicio
    @Autowired
    private TransportistasService transportistasService;

    // inyeccion del mapper
    @Autowired
    private TransportistaMapper transportistaMapper;

    // metodos get
    @GetMapping("/")
    public ResponseEntity<List<getTransportistaDTO>> getAllTransportistas() {
        List<getTransportistaDTO> transportistas = transportistasService.getAllTransportistas().stream()
                .map(transportistaMapper::EntitytoGetDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(transportistas, HttpStatus.OK);
    }

    @GetMapping("/{id_transportista}")
    public ResponseEntity<?> getTransportistaById(@PathVariable Long id_transportista) {
        try {
            getTransportistaDTO transportista = transportistaMapper.EntitytoGetDTO(
                    transportistasService.getTransportistaByid(id_transportista));
            return ResponseEntity.ok(transportista);

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    // ----- fin metodos get

    // metodos POST
    @PostMapping("/")
    public ResponseEntity<?> saveTransportista(@RequestBody saveTransportista saveDTO) {
        try {
            Transportistas transportistaGuardado = transportistasService.saveTransportista(
                    transportistaMapper.EntitytoSaveDTO(saveDTO));
            getTransportistaDTO save = transportistaMapper.EntitytoGetDTO(transportistaGuardado);
            return ResponseEntity.status(HttpStatus.CREATED).body(save);

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // metodos PUT
    @PutMapping("/{id_transportista}")
    public ResponseEntity<?> putTransportista(@RequestBody saveTransportista saveDTO, @PathVariable Long id_transportista) {
        try {
            Transportistas transportistaActualizado = transportistasService.putTransportista(
                    id_transportista, transportistaMapper.EntitytoSaveDTO(saveDTO));
            getTransportistaDTO transportista_actualizado = transportistaMapper.EntitytoGetDTO(transportistaActualizado);
            return ResponseEntity.ok(transportista_actualizado);

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // metodos DELETE
    @DeleteMapping("/{id_transportista}")
    public ResponseEntity<?> deleteTransportista(@PathVariable Long id_transportista) {
        try {
            transportistasService.deleteTransportista(id_transportista);

            return ResponseEntity.ok("Registro eliminado exitosamente");

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
