package com.example.api_transporte.Controller;

import java.util.List;

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

import com.example.api_transporte.Model.EmpresaTransporte;
import com.example.api_transporte.Service.EmpresaTransporteService;

@RestController
@RequestMapping("/api/EmpresaTransporteApi")
public class EmpresaTransporteController {

    // inyeccion del servicio
    @Autowired
    private EmpresaTransporteService empresaTransporteService;

    // metodos get
    @GetMapping("/")
    public ResponseEntity<List<EmpresaTransporte>> getAllEmpresasTrans() {
        List<EmpresaTransporte> empresas = empresaTransporteService.getAllEmpresasTrans();
        return new ResponseEntity<>(empresas, HttpStatus.OK);
    }

    @GetMapping("/{id_empresa}")
    public ResponseEntity<?> getEmpresaTransById(@PathVariable Long id_empresa) {
        try {
            EmpresaTransporte empresa = empresaTransporteService.getEmpresaTransByid(id_empresa);
            return ResponseEntity.ok(empresa);

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    // ----- fin metodos get

    // metodos POST
    @PostMapping("/")
    public ResponseEntity<?> saveEmpresaTrans(@RequestBody EmpresaTransporte empresaNueva) {
        try {
            EmpresaTransporte save = empresaTransporteService.saveEmpresaTrans(empresaNueva);
            return ResponseEntity.status(HttpStatus.CREATED).body(save);

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // metodos PUT
    @PutMapping("/{id_empresa}")
    public ResponseEntity<?> putEmpresaTrans(@RequestBody EmpresaTransporte empresa, @PathVariable Long id_empresa) {
        try {
            EmpresaTransporte empresa_actualizada = empresaTransporteService.putEmpresaTransporte(id_empresa, empresa);
            return ResponseEntity.ok(empresa_actualizada);

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // metodos DELETE
    @DeleteMapping("/{id_empresa}")
    public ResponseEntity<?> deleteEmpresaTrans(@PathVariable Long id_empresa) {
        try {
            empresaTransporteService.deleteEmpresaTrans(id_empresa);

            return ResponseEntity.ok("Registro eliminado exitosamente");

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
