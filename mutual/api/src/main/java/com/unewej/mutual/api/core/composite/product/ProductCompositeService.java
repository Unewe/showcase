package com.unewej.mutual.api.core.composite.product;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@Tag(name = "ProductComposite", description = "REST Api for ProductComposite")
@RestController
public interface ProductCompositeService {
    /**
     * curl $HOST:$PORT/product-composite/1
     * @param id product id
     * @return product or throw not found
     */
    @Operation(summary = "${api.productComposite.getProductComposite.description}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "${api.responseCodes.ok.description}"),
            @ApiResponse(responseCode = "400", description = "${api.responseCodes.badRequest.description}"),
            @ApiResponse(responseCode = "404", description = "${api.responseCodes.notFound.description}"),
            @ApiResponse(responseCode = "422", description = "${api.responseCodes.unprocessableEntity.description}")
    })
    @GetMapping(value = "/product-composite/{id}", produces = "application/json")
    ProductAggregate getProduct(@PathVariable long id);

    @Operation(summary = "${api.productComposite.postProductComposite.description}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "${api.responseCodes.ok.description}"),
            @ApiResponse(responseCode = "400", description = "${api.responseCodes.badRequest.description}"),
            @ApiResponse(responseCode = "404", description = "${api.responseCodes.notFound.description}"),
            @ApiResponse(responseCode = "422", description = "${api.responseCodes.unprocessableEntity.description}")
    })
    @PostMapping(value = "/product-composite", produces = "application/json")
    ProductAggregate createProduct(@RequestBody ProductAggregate product);

    @Operation(summary = "${api.productComposite.putProductComposite.description}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "${api.responseCodes.ok.description}"),
            @ApiResponse(responseCode = "400", description = "${api.responseCodes.badRequest.description}"),
            @ApiResponse(responseCode = "404", description = "${api.responseCodes.notFound.description}"),
            @ApiResponse(responseCode = "422", description = "${api.responseCodes.unprocessableEntity.description}")
    })
    @PutMapping(value = "/product-composite", produces = "application/json")
    ProductAggregate updateProduct(@RequestBody ProductAggregate product);

    @Operation(summary = "${api.productComposite.deleteProductComposite.description}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "${api.responseCodes.ok.description}"),
            @ApiResponse(responseCode = "400", description = "${api.responseCodes.badRequest.description}"),
            @ApiResponse(responseCode = "404", description = "${api.responseCodes.notFound.description}"),
            @ApiResponse(responseCode = "422", description = "${api.responseCodes.unprocessableEntity.description}")
    })
    @DeleteMapping(value = "/product-composite/{id}", produces = "application/json")
    void deleteProduct(@PathVariable long id);
}
