package dh.meli.projeto_integrador.util;

import dh.meli.projeto_integrador.dto.dtoInput.BatchDto;
import dh.meli.projeto_integrador.dto.dtoInput.OrderEntryDto;
import dh.meli.projeto_integrador.dto.dtoInput.SectionDto;

import dh.meli.projeto_integrador.dto.dtoOutput.*;
import dh.meli.projeto_integrador.enumClass.PurchaseOrderStatusEnum;


import dh.meli.projeto_integrador.model.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class Generators {

    public static List<BatchDto> createInboundOrder() {
        Batch batch = createBatch();

        List<BatchDto> batches = new ArrayList<BatchDto>();

        batches.add(new BatchDto(batch));

        return batches;
    }

    public static Batch createBatch() {
        Warehouse warehouse = new Warehouse();

        warehouse.setId(1);
        warehouse.setName("Armazém 01");
        warehouse.setAddress("Rua Almeida 259");

        Section section = new Section();

        section.setId(1);
        section.setCurrentProductLoad(130);
        section.setMaxProductLoad(2000);
        section.setProductType("Fresco");
        section.setWarehouse(warehouse);

        HashSet<Section> sections = new HashSet<Section>();

        sections.add(section);

        warehouse.setSections(sections);

        Product product = new Product();
        product.setId(1);
        product.setCategoryName("Frutas");
        product.setPrice(12.20);
        product.setName("Maçã");
        product.setType("Fresco");

        HashSet<Product> products = new HashSet<Product>();

        products.add(product);

        OrderEntry orderEntry = new OrderEntry();

        orderEntry.setId(1);
        orderEntry.setOrderDate(LocalDate.now());
        orderEntry.setSection(section);

        HashSet<OrderEntry> orderEntries = new HashSet<OrderEntry>();

        orderEntries.add(orderEntry);

        section.setOrderEntries(orderEntries);

        Batch batch01 = new Batch();

        batch01.setId(1);
        batch01.setCurrentTemperature(10);
        batch01.setMinimumTemperature(0);
        batch01.setInitialQuantity(100);
        batch01.setCurrentQuantity(100);
        batch01.setManufacturingDate(LocalDate.now());
        batch01.setManufacturingTime(LocalTime.now());
        batch01.setDueDate(LocalDate.now().plusDays(45));
        batch01.setProduct(product);
        batch01.setOrderEntry(orderEntry);

        HashSet<Batch> batches = new HashSet<Batch>();

        batches.add(batch01);

        orderEntry.setBatches(batches);

        return batch01;
    }

    public static Batch createBatch2() {
        Warehouse warehouse = new Warehouse();

        warehouse.setId(2);
        warehouse.setName("Armazém 02");
        warehouse.setAddress("Rua Botanica 468");

        Section section = new Section();

        section.setId(4);
        section.setCurrentProductLoad(50);
        section.setMaxProductLoad(800);
        section.setProductType("Fresco");
        section.setWarehouse(warehouse);

        HashSet<Section> sections = new HashSet<Section>();

        sections.add(section);

        warehouse.setSections(sections);

        Product product = new Product();
        product.setId(4);
        product.setPrice(20.1);
        product.setCategoryName("Frutas");
        product.setName("Uva");
        product.setType("Fresco");

        HashSet<Product> products = new HashSet<Product>();

        products.add(product);

        OrderEntry orderEntry = new OrderEntry();

        orderEntry.setId(2);
        orderEntry.setOrderDate(LocalDate.now());
        orderEntry.setSection(section);

        HashSet<OrderEntry> orderEntries = new HashSet<OrderEntry>();

        orderEntries.add(orderEntry);

        section.setOrderEntries(orderEntries);

        Batch batch01 = new Batch();

        batch01.setId(2);
        batch01.setCurrentTemperature(15);
        batch01.setMinimumTemperature(0);
        batch01.setInitialQuantity(50);
        batch01.setCurrentQuantity(50);
        batch01.setManufacturingDate(LocalDate.now());
        batch01.setManufacturingTime(LocalTime.now());
        batch01.setDueDate(LocalDate.now());
        batch01.setProduct(product);
        batch01.setOrderEntry(orderEntry);

        HashSet<Batch> batches = new HashSet<Batch>();

        batches.add(batch01);

        orderEntry.setBatches(batches);

        return batch01;
    }

    public static OrderEntryDto createOrderEntryDto() {
        BatchDto batchDto = new BatchDto();

        batchDto.setBatchId(1);
        batchDto.setProductId(1);
        batchDto.setCurrentQuantity(100);
        batchDto.setInitialQuantity(120);
        batchDto.setCurrentTemperature(10);
        batchDto.setMinimumTemperature(0);
        batchDto.setManufacturingDate(LocalDate.now());
        batchDto.setManufacturingTime(LocalTime.now());
        batchDto.setDueDate(LocalDate.now().plusDays(45));

        Set<BatchDto> batchDtoSet = new HashSet<BatchDto>();

        batchDtoSet.add(batchDto);

        SectionDto sectionDto = new SectionDto();

        sectionDto.setSectionId(1);
        sectionDto.setWarehouseId(1);

        OrderEntryDto orderEntryDto = new OrderEntryDto();

        orderEntryDto.setSection(sectionDto);
        orderEntryDto.setAgentId(1);
        orderEntryDto.setBatchStock(batchDtoSet);
        orderEntryDto.setOrderDate(LocalDate.now());

        return orderEntryDto;
    }

    public static OrderEntryDto createOrderEntryDto2() {
        BatchDto batchDto = new BatchDto();

        batchDto.setBatchId(2);
        batchDto.setProductId(4);
        batchDto.setCurrentQuantity(50);
        batchDto.setInitialQuantity(50);
        batchDto.setCurrentTemperature(15);
        batchDto.setMinimumTemperature(0);
        batchDto.setManufacturingDate(LocalDate.now());
        batchDto.setManufacturingTime(LocalTime.now());
        batchDto.setDueDate(LocalDate.now());

        Set<BatchDto> batchDtoSet = new HashSet<BatchDto>();

        batchDtoSet.add(batchDto);

        SectionDto sectionDto = new SectionDto();

        sectionDto.setSectionId(4);
        sectionDto.setWarehouseId(2);

        OrderEntryDto orderEntryDto = new OrderEntryDto();

        orderEntryDto.setSection(sectionDto);
        orderEntryDto.setAgentId(2);
        orderEntryDto.setBatchStock(batchDtoSet);
        orderEntryDto.setOrderDate(LocalDate.now());

        return orderEntryDto;
    }

    public static OrderEntry getOrderEntry() {
        Batch batch = createBatch();

        return batch.getOrderEntry();
    }

    public static OrderEntry getOrderEntry2() {
        Batch batch = createBatch2();

        return batch.getOrderEntry();
    }

    public static Product getProduct() {
        Batch batch = createBatch();

        return batch.getProduct();
    }

    public static Product getProduct2() {
        Batch batch = createBatch2();

        return batch.getProduct();
    }

    public static Warehouse getWarehouse() {
        Batch batch = createBatch();

        return batch.getOrderEntry().getSection().getWarehouse();
    }

    public static Section getSection() {
        Batch batch = createBatch();

        Section section = new Section();
        section.setId(1);
        section.setProductType("Fresco");
        section.setMaxProductLoad(100);
        section.setCurrentProductLoad(10);

        Set<OrderEntry> orderEntries = new HashSet<OrderEntry>();
        OrderEntry orderEntry = batch.getOrderEntry();
        orderEntries.add(orderEntry);

        section.setOrderEntries(orderEntries);

        return batch.getOrderEntry().getSection();
    }

    public static Section getUnavailableSection() {
        Batch batch = createBatch();

        Section section = batch.getOrderEntry().getSection();

        section.setProductType("Congelado");

        return section;
    }

    public static Agent getAgent() {
        Batch batch = createBatch();

        Agent agent = new Agent();
        agent.setId(1);
        agent.setName("João Maria");
        agent.setEmailAddress("joao.maria@gmail.com");
        agent.setPhoneNumber("(48) 999876544");
        agent.setWarehouse(batch.getOrderEntry().getSection().getWarehouse());

        Warehouse warehouse = batch.getOrderEntry().getSection().getWarehouse();

        Set<Agent> agents = new HashSet<Agent>();

        agents.add(agent);

        warehouse.setAgents(agents);

        return agent;
    }

    public static Agent getUnavailableAgent() {
        Agent agent = getAgent();

        Warehouse warehouse = new Warehouse();
        warehouse.setId(10);
        warehouse.setName("Armazém 10");
        warehouse.setAddress("Rua A, 222");

        agent.setWarehouse(warehouse);

        return agent;
    }

    public static Batch getBatch() {
        return createBatch();
    }

    public static List<Batch> getBatches() {
        List<Batch> batchList = new ArrayList<Batch>();

        Batch batch = createBatch();

        batchList.add(batch);

        return batchList;
    }

    public static ProductOutputDto validProductDto1() {
        return ProductOutputDto.builder()
                .name("Maçã")
                .type("Fresco")
                .price(20.1)
                .build();
    }

    public static ProductOutputDto validProductDto2() {
        return ProductOutputDto.builder()
                .name("Uva")
                .type("Fresco")
                .price(20.1)
                .build();
    }

    public static Product validProduct1() {
        return Product.builder()
                .id(1L)
                .name("Maçã")
                .type("Fresco")
                .price(20.1)
                .build();
    }

    public static Product validProduct2() {
        return Product.builder()
                .id(2L)
                .name("sorvete")
                .type("Congelado")
                .price(20.1)
                .build();
    }

    public static List<ProductOutputDto> productDtoList() {
        List<ProductOutputDto> productList = new ArrayList<>();
        productList.add(validProductDto1());
        productList.add(validProductDto2());

        return productList;
    }

    public static List<Product> productList() {
        List<Product> productList = new ArrayList<>();
        productList.add(validProduct1());
        productList.add(validProduct2());

        return productList;
    }

    public static List<Product> emptyProductDtoList() {
        List<Product> productList = new ArrayList<>();
        return productList;
    }

    public static Customer validCustomer1() {
        return Customer.builder()
                .id(1L)
                .cpf("111.111.111-11")
                .name("Alberto")
                .emailAddress("Alberto@email.com")
                .build();
    }

    public static ProductCart validProductCart1() {
        return ProductCart.builder()
                .cart(validCartWhitoutProductCart())
                .product(validProduct1())
                .quantity(5)
                .build();
    }

    public static ProductCart validProductCart2() {
        return ProductCart.builder()
                .cart(validCartWhitoutProductCart())
                .product(validProduct2())
                .quantity(5)
                .build();
    }

    public static Set<ProductCart> validProductCartList() {
        Set<ProductCart> productCartList = new LinkedHashSet<>();
        productCartList.add(validProductCart1());
        productCartList.add(validProductCart2());
        return productCartList;
    }

    public static Cart validCart1() {
        return Cart.builder()
                .id(1L)
                .date(LocalDate.of(2022, 1, 13))
                .status(PurchaseOrderStatusEnum.OPEN)
                .customer(validCustomer1())
                .productCarts(validProductCartList())
                .build();
    }

    public static Cart validCartWhitoutProductCart() {
        return Cart.builder()
                .id(1L)
                .date(LocalDate.of(2022, 1, 13))
                .status(PurchaseOrderStatusEnum.OPEN)
                .customer(validCustomer1())
                .build();
    }

    public static CartProductsOutputDto validCartProductOutputDto1() {
        return CartProductsOutputDto.builder()
                .name("Maçã")
                .type("Fresco")
                .price(20.1)
                .quantity(5)
                .subtotal(100.5)
                .build();
    }

    public static CartProductsOutputDto validCartProductOutputDto2() {
        return CartProductsOutputDto.builder()
                .name("sorvete")
                .type("Congelado")
                .price(20.1)
                .quantity(5)
                .subtotal(100.5)
                .build();
    }

    public static List<CartProductsOutputDto> validProductCartOutputDtoList() {
        List<CartProductsOutputDto> list = new ArrayList<>();
        list.add(validCartProductOutputDto2());
        list.add(validCartProductOutputDto1());
        return list;
    }

    public static CartOutputDto validCartDto() {
        return CartOutputDto.builder()
                .status(PurchaseOrderStatusEnum.OPEN)
                .customerName("Alberto")
                .date(LocalDate.of(2022, 1, 13))
                .products(validProductCartOutputDtoList())
                .total(201.0)
                .build();

    }

    public static ListProductByWarehouseDto getListProductByWarehouseDto() {
        List<TotalProductByWarehouseDto> totalProductByWarehouseDtoList = new ArrayList<TotalProductByWarehouseDto>();

        TotalProductByWarehouseDto totalProductByWarehouseDto01 = new TotalProductByWarehouseDto(1, 100);

        totalProductByWarehouseDtoList.add(totalProductByWarehouseDto01);

        return new ListProductByWarehouseDto(1, totalProductByWarehouseDtoList);
    }

    public static Batch getCleanBatch(Product product, OrderEntry orderEntry) {
        Batch batch = new Batch();

        batch.setId(1);
        batch.setCurrentTemperature(10);
        batch.setMinimumTemperature(0);
        batch.setInitialQuantity(100);
        batch.setCurrentQuantity(100);
        batch.setManufacturingDate(LocalDate.now());
        batch.setManufacturingTime(LocalTime.now());
        batch.setDueDate(LocalDate.now().plusDays(45));
        batch.setProduct(product);
        batch.setOrderEntry(orderEntry);

        return batch;
    }

    public static Warehouse getCleanWarehouse(long id) {
        Warehouse warehouse = new Warehouse();
        warehouse.setId(id);
        warehouse.setName("Armazém 01");
        warehouse.setAddress("Rua Almeida 259");

        return warehouse;
    }

    public static Product getCleanProduct() {
        Product product = new Product();

        product.setName("Peixe");
        product.setPrice(23.90);
        product.setType("Congelado");

        return product;
    }

    public static Section getCleanSection(Warehouse warehouse, int maxProductLoad) {
        Section section = new Section();

        section.setId(1);
        section.setCurrentProductLoad(130);
        section.setMaxProductLoad(maxProductLoad);
        section.setProductType("Fresco");
        section.setWarehouse(warehouse);

        return section;
    }

    public static OrderEntry getCleanOrderEntry(Section section) {
        OrderEntry orderEntry = new OrderEntry();

        orderEntry.setId(1);
        orderEntry.setOrderDate(LocalDate.now());
        orderEntry.setSection(section);

        return orderEntry;
    }

    public static Agent getCleanAgent(Warehouse warehouse) {
        Agent agent = new Agent();

        agent.setName("João Maria");
        agent.setPhoneNumber("(48) 998764332");
        agent.setEmailAddress("joao.maria@gmail.com");
        agent.setWarehouse(warehouse);

        return agent;
    }

    public static OrderEntryDto getCleanOrderEntryDto(long agentId, long warehouseId, long sectionId, long productId) {
        BatchDto batchDto = new BatchDto();

        batchDto.setProductId(productId);
        batchDto.setCurrentQuantity(20);
        batchDto.setInitialQuantity(20);
        batchDto.setCurrentTemperature(10);
        batchDto.setMinimumTemperature(0);
        batchDto.setManufacturingDate(LocalDate.now());
        batchDto.setManufacturingTime(LocalTime.now());
        batchDto.setDueDate(LocalDate.now());

        Set<BatchDto> batchDtoSet = new HashSet<BatchDto>();

        batchDtoSet.add(batchDto);

        SectionDto sectionDto = new SectionDto();

        sectionDto.setSectionId(sectionId);
        sectionDto.setWarehouseId(warehouseId);

        OrderEntryDto orderEntryDto = new OrderEntryDto();

        orderEntryDto.setSection(sectionDto);
        orderEntryDto.setAgentId(agentId);
        orderEntryDto.setBatchStock(batchDtoSet);
        orderEntryDto.setOrderDate(LocalDate.now());

        return orderEntryDto;
    }

    public static Batch createBatchDueDate60Days() {
        Warehouse warehouse = new Warehouse();

        warehouse.setId(0);
        warehouse.setName("Armazém 01");
        warehouse.setAddress("Rua Almeida 259");

        Section section = new Section();

        section.setId(0);
        section.setCurrentProductLoad(130);
        section.setMaxProductLoad(2000);
        section.setProductType("Fresco");
        section.setWarehouse(warehouse);

        HashSet<Section> sections = new HashSet<Section>();

        Agent agent = new Agent();
        agent.setId(0);
        agent.setName("João Maria");
        agent.setPhoneNumber("48 999343899");
        agent.setEmailAddress("joaomaria@gmail.com");
        agent.setWarehouse(warehouse);

        HashSet<Agent> agents = new HashSet<Agent>();
        agents.add(agent);

        sections.add(section);

        warehouse.setSections(sections);
        warehouse.setAgents(agents);

        Product product = new Product();
        product.setPrice(12.20);
        product.setId(1);
        product.setName("Maçã");
        product.setType("Fresco");

        OrderEntry orderEntry = new OrderEntry();

        orderEntry.setId(1);
        orderEntry.setOrderDate(LocalDate.now());
        orderEntry.setSection(section);

        HashSet<OrderEntry> orderEntries = new HashSet<OrderEntry>();

        orderEntries.add(orderEntry);

        section.setOrderEntries(orderEntries);

        Batch batch01 = new Batch();

        batch01.setId(1);
        batch01.setCurrentTemperature(10);
        batch01.setMinimumTemperature(0);
        batch01.setInitialQuantity(100);
        batch01.setCurrentQuantity(100);
        batch01.setManufacturingDate(LocalDate.now());
        batch01.setManufacturingTime(LocalTime.now());
        batch01.setDueDate(LocalDate.now().plusDays(60));
        batch01.setProduct(product);
        batch01.setOrderEntry(orderEntry);

        HashSet<Batch> batches = new HashSet<Batch>();

        batches.add(batch01);

        orderEntry.setBatches(batches);

        return batch01;
    }

    public static Batch createBatchDueDate90Days() {
        Warehouse warehouse = new Warehouse();

        warehouse.setId(0);
        warehouse.setName("Armazém 01");
        warehouse.setAddress("Rua Almeida 259");

        Section section = new Section();

        section.setId(0);
        section.setCurrentProductLoad(130);
        section.setMaxProductLoad(2000);
        section.setProductType("Fresco");
        section.setWarehouse(warehouse);

        HashSet<Section> sections = new HashSet<Section>();

        Agent agent = new Agent();
        agent.setId(0);
        agent.setName("João Maria");
        agent.setPhoneNumber("48 999343899");
        agent.setEmailAddress("joaomaria@gmail.com");
        agent.setWarehouse(warehouse);

        HashSet<Agent> agents = new HashSet<Agent>();
        agents.add(agent);

        sections.add(section);

        warehouse.setSections(sections);
        warehouse.setAgents(agents);

        Product product = new Product();
        product.setPrice(12.20);
        product.setId(1);
        product.setName("Maçã");
        product.setType("Fresco");

        OrderEntry orderEntry = new OrderEntry();

        orderEntry.setId(1);
        orderEntry.setOrderDate(LocalDate.now());
        orderEntry.setSection(section);

        HashSet<OrderEntry> orderEntries = new HashSet<OrderEntry>();

        orderEntries.add(orderEntry);

        section.setOrderEntries(orderEntries);

        Batch batch01 = new Batch();

        batch01.setId(2);
        batch01.setCurrentTemperature(10);
        batch01.setMinimumTemperature(0);
        batch01.setInitialQuantity(100);
        batch01.setCurrentQuantity(10);
        batch01.setManufacturingDate(LocalDate.now());
        batch01.setManufacturingTime(LocalTime.now());
        batch01.setDueDate(LocalDate.now().plusDays(90));
        batch01.setProduct(product);
        batch01.setOrderEntry(orderEntry);

        HashSet<Batch> batches = new HashSet<Batch>();

        batches.add(batch01);

        orderEntry.setBatches(batches);

        return batch01;
    }

    public static Batch createBatchDueDate21Days() {
        Warehouse warehouse = new Warehouse();

        warehouse.setId(0);
        warehouse.setName("Armazém 01");
        warehouse.setAddress("Rua Almeida 259");

        Section section = new Section();

        section.setId(0);
        section.setCurrentProductLoad(130);
        section.setMaxProductLoad(2000);
        section.setProductType("Fresco");
        section.setWarehouse(warehouse);

        HashSet<Section> sections = new HashSet<Section>();

        Agent agent = new Agent();
        agent.setId(0);
        agent.setName("João Maria");
        agent.setPhoneNumber("48 999343899");
        agent.setEmailAddress("joaomaria@gmail.com");
        agent.setWarehouse(warehouse);

        HashSet<Agent> agents = new HashSet<Agent>();
        agents.add(agent);

        sections.add(section);

        warehouse.setSections(sections);
        warehouse.setAgents(agents);

        Product product = new Product();
        product.setPrice(12.20);
        product.setId(1);
        product.setName("Fresco");
        product.setType("Fruta");

        OrderEntry orderEntry = new OrderEntry();

        orderEntry.setId(1);
        orderEntry.setOrderDate(LocalDate.now());
        orderEntry.setSection(section);

        HashSet<OrderEntry> orderEntries = new HashSet<OrderEntry>();

        orderEntries.add(orderEntry);

        section.setOrderEntries(orderEntries);

        Batch batch01 = new Batch();

        batch01.setId(1);
        batch01.setCurrentTemperature(10);
        batch01.setMinimumTemperature(0);
        batch01.setInitialQuantity(100);
        batch01.setCurrentQuantity(80);
        batch01.setManufacturingDate(LocalDate.now());
        batch01.setManufacturingTime(LocalTime.now());
        batch01.setDueDate(LocalDate.now().plusDays(21));
        batch01.setProduct(product);
        batch01.setOrderEntry(orderEntry);

        HashSet<Batch> batches = new HashSet<Batch>();

        batches.add(batch01);

        orderEntry.setBatches(batches);

        return batch01;
    }


    public static List<Batch> validBatchList() {
        List<Batch> batchList = new ArrayList<>();
        batchList.add(createBatchDueDate21Days());
        batchList.add(createBatchDueDate60Days());
        batchList.add(createBatchDueDate90Days());
        return batchList;
    }

    public static List<Batch> notAbleBatchList() {
        List<Batch> batchList = new ArrayList<>();
        batchList.add(createBatchDueDate21Days());
        return batchList;
    }

    public static List<Batch> emptyBatchList() {
        return new ArrayList<>();
    }

    public static ProductStockDto getProductStockDtos() {
        return new ProductStockDto(getProduct(), validBatchList());

    }

    public static Warehouse createWarehouse() {
        Warehouse warehouse = new Warehouse();
        warehouse.setId(1);
        warehouse.setName("Armazém 01");
        warehouse.setAddress("Rua Almeida 259");
        return warehouse;
    }

    public static Section createSectionFresco() {
        Section section = new Section();
        section.setId(1);
        section.setCurrentProductLoad(130);
        section.setMaxProductLoad(2000);
        section.setProductType("Fresco");
        section.setWarehouse(createWarehouse());
        return section;
    }

    public static Section createSectionRefrigerado() {
        Section section = new Section();
        section.setId(2);
        section.setCurrentProductLoad(130);
        section.setMaxProductLoad(2000);
        section.setProductType("Fresco");
        section.setWarehouse(createWarehouse());
        return section;
    }

    public static Section createSectionCongelado() {
        Section section = new Section();
        section.setId(3);
        section.setCurrentProductLoad(130);
        section.setMaxProductLoad(1000);
        section.setProductType("Fresco");
        section.setWarehouse(createWarehouse());
        return section;
    }

    public static Agent createAgent() {
        Agent agent = new Agent();
        agent.setId(1);
        agent.setName("João Maria");
        agent.setPhoneNumber("48 999343899");
        agent.setEmailAddress("joaomaria@gmail.com");
        agent.setWarehouse(createWarehouse());
        return agent;
    }

    public static OrderEntry createOrderEntry() {
        OrderEntry orderEntry = new OrderEntry();
        orderEntry.setId(1);
        orderEntry.setOrderDate(LocalDate.now());
        orderEntry.setSection(createSectionFresco());
        return orderEntry;
    }

    public static List<BatchStockDto> getBatchStockDto(Batch batch, Product product) {
        BatchStockDto batchStockDto = new BatchStockDto();

        batchStockDto.setBatchNumber(batch.getId());
        batchStockDto.setProductId(product.getId());
        batchStockDto.setProductType(product.getType());
        batchStockDto.setQuantity(batch.getCurrentQuantity());
        batchStockDto.setDueDate(batch.getDueDate());

        List<BatchStockDto> batchStockDtoList = new ArrayList<BatchStockDto>();

        batchStockDtoList.add(batchStockDto);

        return batchStockDtoList;
    }
}
