package com.app.cbouix.sodapp.DataAccess.DataBase;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table COBRANZA_LIN.
 */
public class CobranzaLin {

    private Long id;
    private Long CobranzaId;
    private Long MedioCzaId;
    private String MedioCzaCod;
    private String MedioCzaNombre;
    private Double Importe;
    private String NroDocumento;
    private String Vencimiento;
    private String BancoId;
    private String BancoCod;
    private String BancoNombre;
    private String TarjCreditoId;
    private String TarjCreditoCod;
    private String TarjCreditoNombre;
    private String TarjDebitoId;
    private String TarjDebitoCod;
    private String TarjDebitoNombre;
    private String TarjNumero;
    private String TarjOperacion;
    private String TarjCuotas;
    private Double ImporteBruto;

    public CobranzaLin() {
    }

    public CobranzaLin(Long id) {
        this.id = id;
    }

    public CobranzaLin(Long id, Long CobranzaId, Long MedioCzaId, String MedioCzaCod, String MedioCzaNombre, Double Importe, String NroDocumento, String Vencimiento, String BancoId, String BancoCod, String BancoNombre, String TarjCreditoId, String TarjCreditoCod, String TarjCreditoNombre, String TarjDebitoId, String TarjDebitoCod, String TarjDebitoNombre, String TarjNumero, String TarjOperacion, String TarjCuotas, Double ImporteBruto) {
        this.id = id;
        this.CobranzaId = CobranzaId;
        this.MedioCzaId = MedioCzaId;
        this.MedioCzaCod = MedioCzaCod;
        this.MedioCzaNombre = MedioCzaNombre;
        this.Importe = Importe;
        this.NroDocumento = NroDocumento;
        this.Vencimiento = Vencimiento;
        this.BancoId = BancoId;
        this.BancoCod = BancoCod;
        this.BancoNombre = BancoNombre;
        this.TarjCreditoId = TarjCreditoId;
        this.TarjCreditoCod = TarjCreditoCod;
        this.TarjCreditoNombre = TarjCreditoNombre;
        this.TarjDebitoId = TarjDebitoId;
        this.TarjDebitoCod = TarjDebitoCod;
        this.TarjDebitoNombre = TarjDebitoNombre;
        this.TarjNumero = TarjNumero;
        this.TarjOperacion = TarjOperacion;
        this.TarjCuotas = TarjCuotas;
        this.ImporteBruto = ImporteBruto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCobranzaId() {
        return CobranzaId;
    }

    public void setCobranzaId(Long CobranzaId) {
        this.CobranzaId = CobranzaId;
    }

    public Long getMedioCzaId() {
        return MedioCzaId;
    }

    public void setMedioCzaId(Long MedioCzaId) {
        this.MedioCzaId = MedioCzaId;
    }

    public String getMedioCzaCod() {
        return MedioCzaCod;
    }

    public void setMedioCzaCod(String MedioCzaCod) {
        this.MedioCzaCod = MedioCzaCod;
    }

    public String getMedioCzaNombre() {
        return MedioCzaNombre;
    }

    public void setMedioCzaNombre(String MedioCzaNombre) {
        this.MedioCzaNombre = MedioCzaNombre;
    }

    public Double getImporte() {
        return Importe;
    }

    public void setImporte(Double Importe) {
        this.Importe = Importe;
    }

    public String getNroDocumento() {
        return NroDocumento;
    }

    public void setNroDocumento(String NroDocumento) {
        this.NroDocumento = NroDocumento;
    }

    public String getVencimiento() {
        return Vencimiento;
    }

    public void setVencimiento(String Vencimiento) {
        this.Vencimiento = Vencimiento;
    }

    public String getBancoId() {
        return BancoId;
    }

    public void setBancoId(String BancoId) {
        this.BancoId = BancoId;
    }

    public String getBancoCod() {
        return BancoCod;
    }

    public void setBancoCod(String BancoCod) {
        this.BancoCod = BancoCod;
    }

    public String getBancoNombre() {
        return BancoNombre;
    }

    public void setBancoNombre(String BancoNombre) {
        this.BancoNombre = BancoNombre;
    }

    public String getTarjCreditoId() {
        return TarjCreditoId;
    }

    public void setTarjCreditoId(String TarjCreditoId) {
        this.TarjCreditoId = TarjCreditoId;
    }

    public String getTarjCreditoCod() {
        return TarjCreditoCod;
    }

    public void setTarjCreditoCod(String TarjCreditoCod) {
        this.TarjCreditoCod = TarjCreditoCod;
    }

    public String getTarjCreditoNombre() {
        return TarjCreditoNombre;
    }

    public void setTarjCreditoNombre(String TarjCreditoNombre) {
        this.TarjCreditoNombre = TarjCreditoNombre;
    }

    public String getTarjDebitoId() {
        return TarjDebitoId;
    }

    public void setTarjDebitoId(String TarjDebitoId) {
        this.TarjDebitoId = TarjDebitoId;
    }

    public String getTarjDebitoCod() {
        return TarjDebitoCod;
    }

    public void setTarjDebitoCod(String TarjDebitoCod) {
        this.TarjDebitoCod = TarjDebitoCod;
    }

    public String getTarjDebitoNombre() {
        return TarjDebitoNombre;
    }

    public void setTarjDebitoNombre(String TarjDebitoNombre) {
        this.TarjDebitoNombre = TarjDebitoNombre;
    }

    public String getTarjNumero() {
        return TarjNumero;
    }

    public void setTarjNumero(String TarjNumero) {
        this.TarjNumero = TarjNumero;
    }

    public String getTarjOperacion() {
        return TarjOperacion;
    }

    public void setTarjOperacion(String TarjOperacion) {
        this.TarjOperacion = TarjOperacion;
    }

    public String getTarjCuotas() {
        return TarjCuotas;
    }

    public void setTarjCuotas(String TarjCuotas) {
        this.TarjCuotas = TarjCuotas;
    }

    public Double getImporteBruto() {
        return ImporteBruto;
    }

    public void setImporteBruto(Double ImporteBruto) {
        this.ImporteBruto = ImporteBruto;
    }

}
