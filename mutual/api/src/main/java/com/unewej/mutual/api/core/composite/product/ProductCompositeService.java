package com.unewej.mutual.api.core.composite.product;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "ProductComposite", description = "REST Api for ProductComposite.")
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
    ProductAggregate getProduct(@PathVariable int id);
}
