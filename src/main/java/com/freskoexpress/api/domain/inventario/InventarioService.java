package com.freskoexpress.api.domain.inventario;

import com.freskoexpress.api.domain.inventario.dto.*;
import com.freskoexpress.api.domain.proveedor.Proveedor;
import com.freskoexpress.api.domain.proveedor.ProveedorRepository;
import com.freskoexpress.api.shared.exception.BusinessException;
import com.freskoexpress.api.shared.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class InventarioService {

    private final ProductoRepository  productoRepository;
    private final LoteRepository      loteRepository;
    private final ProveedorRepository proveedorRepository;

    public InventarioService(ProductoRepository productoRepository,
                             LoteRepository loteRepository,
                             ProveedorRepository proveedorRepository) {
        this.productoRepository  = productoRepository;
        this.loteRepository      = loteRepository;
        this.proveedorRepository = proveedorRepository;
    }

    @Transactional
    public ProductoResponse crearProducto(CrearProductoRequest request) {
        Proveedor proveedor = proveedorRepository.findById(request.idProveedor())
                .orElseThrow(() -> new ResourceNotFoundException("Proveedor no encontrado"));
        if (request.tempMinC() != null && request.tempMaxC() != null
                && request.tempMinC().compareTo(request.tempMaxC()) >= 0) {
            throw new BusinessException("temp_min_c debe ser menor que temp_max_c");
        }
        Producto producto = Producto.builder()
                .proveedor(proveedor)
                .nombre(request.nombre())
                .categoria(request.categoria())
                .unidadMedida(request.unidadMedida())
                .tempMinC(request.tempMinC())
                .tempMaxC(request.tempMaxC())
                .stockMinimo(request.stockMinimo())
                .precioUnitario(request.precioUnitario())
                .activo(true)
                .build();
        return ProductoResponse.from(productoRepository.save(producto));
    }

    @Transactional
    public LoteResponse ingresarLote(CrearLoteRequest request) {
        Producto producto = productoRepository.findById(request.idProducto())
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado"));
        Proveedor proveedor = proveedorRepository.findById(request.idProveedor())
                .orElseThrow(() -> new ResourceNotFoundException("Proveedor no encontrado"));
        if (!request.fechaVencimiento().isAfter(request.fechaIngreso())) {
            throw new BusinessException(
                    "La fecha de vencimiento debe ser posterior a la de ingreso");
        }
        Lote lote = Lote.builder()
                .producto(producto)
                .proveedor(proveedor)
                .numeroLote(request.numeroLote())
                .cantidadActual(request.cantidadActual())
                .fechaIngreso(request.fechaIngreso())
                .fechaVencimiento(request.fechaVencimiento())
                .build();
        return LoteResponse.from(loteRepository.save(lote));
    }

    public List<LoteResponse> lotesFIFO(Integer idProducto) {
        return loteRepository.findActivosByProductoFIFO(idProducto)
                .stream().map(LoteResponse::from).toList();
    }

    public List<LoteResponse> lotesProximosAVencer(int dias) {
        return loteRepository.findLotesProximosAVencer(LocalDate.now().plusDays(dias))
                .stream().map(LoteResponse::from).toList();
    }

    public List<ProductoResponse> productosConStockBajo() {
        return productoRepository.findProductosBajoStockMinimo()
                .stream().map(ProductoResponse::from).toList();
    }

    public List<ProductoResponse> listarCatalogo() {
        return productoRepository.findByActivoTrue()
                .stream().map(ProductoResponse::from).toList();
    }
}