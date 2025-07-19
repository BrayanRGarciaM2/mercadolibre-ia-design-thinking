package com.g18.experimentodesignthinking

import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil.compose.AsyncImage
import com.g18.experimentodesignthinking.ui.theme.ExperimentoDesignThinkingTheme
import kotlin.math.roundToInt

// ===== CONFIGURACIÓN DE LA IA =====
// Cambia este valor antes de compilar para controlar el comportamiento de precios de la IA:
// true = La IA no sugiere versiones más baratas del mismo producto seleccionado
// false = La IA sugiere productos sin restricción de precio
var aiPriceControlEnabled = true

// Datos mockeados para los productos base
val baseProducts = listOf(
    Product(
        title = "Adidas Botella Metálica Deportiva",
        store = "Adidas",
        price = "$75.900",
        imageUrl = "https://assets.adidas.com/images/h_2000,f_auto,q_auto,fl_lossy,c_fill,g_auto/0cbba516fdf64c43a5791b100bd259b3_9366/Botella_Metalica_con_Tapon_de_Rosca_de_06_l_Negro_KC6412_01_00_standard.jpg",
        label = "Deportivo"
    ),
    Product(
        title = "Termo Nike Hyperfuel Squeeze",
        store = "Nike",
        price = "$75.900",
        imageUrl = "https://todoendeportes.com.co/wp-content/uploads/2023/12/Nike-Hyperfuel-Squeeze-32oz-Negro_Amarillo.jpg",
        label = "Deportivo"
    ),
    Product(
        title = "Termo Acero Inoxidable 500ml",
        store = "Hogar y Más",
        price = "$45.900",
        imageUrl = "https://mktimg.kwcdn.com/material-image/fancy/dpa/ebdfc24f57c77dcbc1ac3061979f6432_1FCSNUxGfonot.jpg?imageView2/2/w/800/q/70/format/webp",
        label = "IA"
    ),
    Product(
        title = "Termo Deportivo Azul",
        store = "Deportes Andes",
        price = "$71.900",
        imageUrl = "https://media.falabella.com/sodimacCO/757280/w=1066,h=832,f=webp,fit=contain,q=85",
        label = "Deportivo"
    ),
    Product(
        title = "Termo Café 1L",
        store = "Café Express",
        price = "$57.600",
        imageUrl = "https://pepeganga.vtexassets.com/arquivos/ids/614694-1600-auto?v=637636193288300000&width=1600&height=auto&aspect=true",
        label = "IA"
    ),
    Product(
        title = "Termo Infantil Animales",
        store = "Kids Store",
        price = "$132.900",
        imageUrl = "https://m.media-amazon.com/images/I/71yjyNT9pOL._AC_SL1500_.jpg",
        label = "Termo animales"
    ),
    Product(
        title = "Termo Viaje Negro Mate",
        store = "TravelPro",
        price = "$58.446",
        imageUrl = "https://img-1.kwcdn.com/product/fancy/98670053-9d36-4c30-986e-1bb264ea3cd0.jpg?imageView2/2/w/800/q/70/format/webp",
        label = "Termo Viaje"
    ),
    Product(
        title = "Termo Office Gris 750ml",
        store = "OfiMarket",
        price = "$80.000",
        imageUrl = "https://img-1.kwcdn.com/product/fancy/f4987990-2ebb-4f2a-b1ec-b90b384d9e34.jpg?imageView2/2/w/800/q/70/format/webp",
        label = "Termo Office"
    ),
    Product(
        title = "Termo Personalizado",
        store = "RegalosYA",
        price = "$67.500",
        imageUrl = "https://img-1.kwcdn.com/product/open/a1dfc0f617a6447399e80a27c7b7f238-goods.jpeg?imageView2/2/w/800/q/70/format/webp",
        label = "IA"
    ),
    Product(
        title = "Termo Infantil c",
        store = "Kids Store",
        price = "$59.000",
        imageUrl = "https://img-1.kwcdn.com/product/fancy/a50500cd-12c3-47cf-8b62-3eadc9fed2c3.jpg?imageView2/2/w/800/q/70/format/webp",
        label = "IA"
    ),
    Product(
        title = "Termo Outdoor Verde Militar",
        store = "Outdoor Gear",
        price = "$188.800",
        imageUrl = "https://img.ltwebstatic.com/images3_spmp/2024/07/15/91/172105655184c83f3fd945eade2a09f953c33363ef_thumbnail_560x.webp",
        label = "Termo Outdoor"
    ),
    Product(
        title = "Termo Doble Pared 1.2L",
        store = "Hogar y Más",
        price = "$55.000",
        imageUrl = "https://m.media-amazon.com/images/I/81o1Fe4ELoL._AC_SL1500_.jpg",
        label = "Termo Doble Pared"
    ),
    Product(
        title = "Termo Urbano Azul Oscuro",
        store = "Urban Style",
        price = "$50.500",
        imageUrl = "https://pepeganga.vtexassets.com/arquivos/ids/967157-800-auto?v=638231526633900000&width=800&height=auto&aspect=true",
        label = "Termo Urbano Azul"
    ),
    Product(
        title = "Termo con Infusor",
        store = "Tea Lovers",
        price = "$66.900",
        imageUrl = "https://img-1.kwcdn.com/product/fancy/5707c9e1-bca6-42c1-ba81-f8046ff564ad.jpg?imageView2/2/w/800/q/70/format/webp",
        label = "Termo con Infusor"
    ),
    Product(
        title = "Termo Vintage Acero",
        store = "ClassicHome",
        price = "$77.000",
        imageUrl = "https://img-1.kwcdn.com/product/Fancyalgo/VirtualModelMatting/d6ae348493c1d314d6c01bb886f233c5.jpg?imageView2/2/w/800/q/70/format/webp",
        label = "Termo Vintage Acero"
    ),
    Product(
        title = "Termo Slim Blanco",
        store = "Minimal Store",
        price = "$48.000",
        imageUrl = "https://http2.mlstatic.com/D_NQ_NP_2X_644030-MLU74218007358_012024-F.webp",
        label = "Termo Slim Blanco"
    ),
    Product(
        title = "Termo Rojo Brillante",
        store = "Hogar y Más",
        price = "$45.500",
        imageUrl = "https://img-1.kwcdn.com/product/fancy/11d33ac4-38a7-4db5-a3be-839809b2e053.jpg?imageView2/2/w/800/q/70/format/webp",
        label = "Termo Rojo Brillante"
    ),
    Product(
        title = "Termo con Pajilla",
        store = "Deportes Andes",
        price = "$49.900",
        imageUrl = "https://img-1.kwcdn.com/product/fancy/bd7cdcbd-0d6d-428c-86e8-6fb7525865af.jpg?imageView2/2/w/800/q/70/format/webp",
        label = "Termo con Pajilla"
    ),
    Product(
        title = "Termo Familiar 2L",
        store = "Hogar y Más",
        price = "$59.000",
        imageUrl = "https://contents.mediadecathlon.com/p2421852/1cr1/k\$bbaa57ad33ac7b019d8cf7ab70df49b9/botella-galon-bodybuilding-gris-22-l.jpg?format=auto&f=768x0",
        label = "Termo Familiar 2L"
    ),
    Product(
        title = "Termo Premium Dorado",
        store = "Luxury Store",
        price = "$34.500",
        imageUrl = "https://img-1.kwcdn.com/product/fancy/01d1fbbe-1fcd-431a-a8f2-a78c5072a655.jpg?imageView2/2/w/800/q/70/format/webp",
        label = "Termo Premium Dorado"
    ),
    Product(
        title = "Termo Rosa Pastel",
        store = "Moda Urbana",
        price = "$56.500",
        imageUrl = "https://m.media-amazon.com/images/I/51bhuWp4FbL._AC_SL1500_.jpg",
        label = "Termo Rosa Pastel"
    ),
    Product(
        title = "Termo Compacto Gris Claro",
        store = "OfiMarket",
        price = "$40.000",
        imageUrl = "https://img-1.kwcdn.com/product/fancy/60925b77-70f2-4468-9320-6b974445da7d.jpg?imageView2/2/w/800/q/70/format/webp",
        label = "Termo Compacto Gris Claro"
    )
)

// Función para generar productos con precios variables
fun generateInfiniteProducts(searchQuery: String = ""): List<Product> {
    val infiniteProducts = mutableListOf<Product>()
    val basePrices = listOf(149900, 129500, 99900, 189000, 85000, 110750)

    // Generar 100 productos (puedes ajustar este número)
    repeat(100) { index ->
        val baseProductIndex = index % baseProducts.size
        val baseProduct = baseProducts[baseProductIndex]
        val basePrice = basePrices[baseProductIndex]

        // Variar el precio: ±20% del precio base
        val priceVariation = (basePrice * 0.8 + (basePrice * 0.4 * (index % 10) / 10.0)).toInt()
        val formattedPrice = "$${String.format("%,d", priceVariation)}"

        // Si es el primer producto (recomendado por IA), asegurar que tenga el menor precio
        val finalPrice = if (baseProductIndex == 0) {
            val minPrice = basePrices.minOrNull() ?: basePrice
            "$${String.format("%,d", minPrice)}"
        } else {
            formattedPrice
        }

        infiniteProducts.add(
            Product(
                title = baseProduct.title,
                store = baseProduct.store,
                price = finalPrice,
                imageUrl = baseProduct.imageUrl,
                label = if (baseProductIndex == 0) "Recomendado IA" else baseProduct.label
            )
        )
    }

    return infiniteProducts
}

// Función para generar un producto aleatorio con mayor probabilidad para el recomendado por IA
fun generateRandomProduct(searchQuery: String = ""): Product {
    val basePrices = baseProducts.map {
        it.price.replace("$", "").replace(".", "").toIntOrNull() ?: 0
    }
    
    // Obtener el producto principal de la IA para comparación
    val mainAIProduct = baseProducts.firstOrNull { it.label == "Deportivo" } ?: baseProducts.first()
    val mainPrice = mainAIProduct.price.replace("$", "").replace(".", "").toIntOrNull() ?: 75000
    val mainTitle = mainAIProduct.title
    
    // Determinar probabilidades basadas en la búsqueda
    val isPlasticoSearch = searchQuery.lowercase().contains("plastico")
    
    // Productos específicos que deben aparecer más cuando se busca "plastico"
    val plasticoProducts = listOf(
        "Termo Nike Hyperfuel Squeeze",
        "Termo Deportivo Azul"
    )
    
    // Encontrar índices de los productos específicos
    val plasticoProductIndices = baseProducts.mapIndexedNotNull { index, product ->
        if (plasticoProducts.contains(product.title)) index else null
    }
    
    val random = (0..99).random()
    val baseProductIndex = if (isPlasticoSearch && plasticoProductIndices.isNotEmpty()) {
        // Si es búsqueda de plástico, 30% de probabilidad para productos específicos de plástico
        if (random < 30) {
            plasticoProductIndices.random()
        } else if (random < 50) {
            0 // Producto recomendado por IA (20% de probabilidad)
        } else {
            // Resto de productos (50% de probabilidad)
            val availableIndices = (1 until baseProducts.size).filter { it !in plasticoProductIndices }
            if (availableIndices.isNotEmpty()) availableIndices.random() else 0
        }
    } else {
        // Comportamiento normal: 20% de probabilidad para el producto recomendado por IA
        if (random < 20) {
            0 // Producto recomendado por IA
        } else {
            (1 until baseProducts.size).random() // Otros productos
        }
    }
    val baseProduct = baseProducts[baseProductIndex]
    val basePrice = basePrices[baseProductIndex]
    
    // Variar el precio: ±20% del precio base
    var priceVariation = (basePrice * 0.8 + (basePrice * 0.4 * (0..10).random() / 10.0)).toInt()
    
    // Si la bandera está habilitada y es el mismo producto que el principal, asegurar que no sea más barato
    if (aiPriceControlEnabled && baseProduct.title == mainTitle) {
        // Si el precio calculado es menor al principal, generar un precio aleatorio mayor o igual
        if (priceVariation < mainPrice) {
            // Generar un precio entre el principal y un 30% más caro
            val maxPrice = (mainPrice * 1.3).toInt()
            priceVariation = (mainPrice..maxPrice).random()
        }
    }
    
    val formattedPrice = "$${String.format("%,d", priceVariation)}"
    
    // Solo el producto específico elegido por la IA tiene el borde especial
    val isTheSpecificAIProduct = false // Nunca será true para productos generados aleatoriamente
    return Product(
        title = baseProduct.title,
        store = baseProduct.store,
        price = formattedPrice,
        imageUrl = baseProduct.imageUrl,
        label = null, // Los productos generados aleatoriamente nunca tienen label
        isRecommendedByAI = isTheSpecificAIProduct
    )
}

data class Product(
    val title: String,
    val store: String,
    val price: String,
    val imageUrl: String,
    val label: String? = null,
    val isRecommendedByAI: Boolean = false
)

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ExperimentoDesignThinkingTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "main") {
                    composable("main") {
                        MainScreen(navController)
                    }
                    composable(
                        "results/{searchQuery}",
                        arguments = listOf(
                            navArgument("searchQuery") { type = NavType.StringType }
                        )
                    ) { backStackEntry ->
                        val searchQuery = backStackEntry.arguments?.getString("searchQuery") ?: ""
                        ResultsScreen(navController, searchQuery)
                    }
                    composable(
                        "detail/{title}/{imageUrl}/{price}/{isRecommendedByAI}",
                        arguments = listOf(
                            navArgument("title") { type = NavType.StringType },
                            navArgument("imageUrl") { type = NavType.StringType },
                            navArgument("price") { type = NavType.StringType },
                            navArgument("isRecommendedByAI") { type = NavType.BoolType }
                        )
                    ) { backStackEntry ->
                        val title = backStackEntry.arguments?.getString("title") ?: ""
                        val imageUrl = backStackEntry.arguments?.getString("imageUrl") ?: ""
                        val price = backStackEntry.arguments?.getString("price") ?: ""
                        val isRecommendedByAI =
                            backStackEntry.arguments?.getBoolean("isRecommendedByAI") ?: false
                        ProductDetailScreen(
                            title,
                            imageUrl,
                            price,
                            navController,
                            isRecommendedByAI
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: androidx.navigation.NavHostController) {
    var searchText by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFFFFE600) // Fondo amarillo Mercado Libre
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Toolbar con barra de búsqueda
            TopAppBar(
                title = {
                    TextField(
                        value = searchText,
                        onValueChange = { searchText = it },
                        placeholder = {
                            androidx.compose.material3.Text(
                                "Buscar en Mercado Libre",
                                color = Color.Gray,
                                fontSize = 18.sp
                            )
                        },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp), // Solo padding, sin height fijo grande
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            disabledContainerColor = Color.White,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent
                        ),
                        shape = RoundedCornerShape(24.dp),
                        textStyle = TextStyle(
                            color = Color.Black,
                            fontSize = 18.sp
                        ),
                        leadingIcon = {
                                                                Icon(
                                        imageVector = Icons.Default.Search,
                                        contentDescription = "Buscar",
                                        tint = Color.Gray,
                                        modifier = Modifier
                                            .size(24.dp)
                                            .clickable {
                                                if (searchText.isNotBlank()) {
                                                    navController.navigate("results/${Uri.encode(searchText)}")
                                                    focusManager.clearFocus()
                                                }
                                            }
                                    )
                        },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Search
                        ),
                        keyboardActions = KeyboardActions(
                            onSearch = {
                                if (searchText.isNotBlank()) {
                                    navController.navigate("results/${Uri.encode(searchText)}")
                                    focusManager.clearFocus()
                                }
                            }
                        )
                    )
                },
                colors = androidx.compose.material3.TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFFFE600) // Fondo amarillo
                )
            )
            // Imagen debajo del toolbar
            val context = LocalContext.current
            val imageBitmap = remember {
                val inputStream = context.assets.open("img.png")
                val bitmap = BitmapFactory.decodeStream(inputStream)
                inputStream.close()
                bitmap?.asImageBitmap()
            }
            if (imageBitmap != null) {
                Image(
                    bitmap = imageBitmap,
                    contentDescription = "Fondo Mercado Libre",
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResultsScreen(navController: androidx.navigation.NavHostController, initialSearchQuery: String = "") {
    var searchText by remember { mutableStateOf("") }
    var showChat by remember { mutableStateOf(false) }
    var chatMessage by remember { mutableStateOf("") }
    var aiSelectedProducts by remember { mutableStateOf<List<Product>>(emptyList()) }
    var isAISearchActive by remember { mutableStateOf(false) }
    var showHelpMessage by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5)) // Fondo gris como en la imagen
    ) {
        // Definir la altura de pantalla y helpCardY para ambos elementos
        val configuration = LocalConfiguration.current
        val screenHeightPx = with(LocalDensity.current) { configuration.screenHeightDp.dp.toPx() }
        val helpCardY = (screenHeightPx * 0.75f).roundToInt() // 75% desde arriba (25% desde abajo)

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            TopAppBar(
                title = {
                    TextField(
                        value = searchText,
                        onValueChange = { searchText = it },
                        placeholder = {
                            androidx.compose.material3.Text(
                                "Buscar en Mercado Libre",
                                color = Color.Gray,
                                fontSize = 18.sp
                            )
                        },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            disabledContainerColor = Color.White,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent
                        ),
                        shape = RoundedCornerShape(24.dp),
                        textStyle = TextStyle(
                            color = Color.Black,
                            fontSize = 18.sp
                        ),
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Buscar",
                                tint = Color.Gray,
                                modifier = Modifier.size(24.dp)
                            )
                        },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Search
                        ),
                        keyboardActions = KeyboardActions(
                            onSearch = {
                                if (searchText.isNotBlank()) {
                                    focusManager.clearFocus()
                                }
                            }
                        )
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        }
                    ) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                },
                colors = androidx.compose.material3.TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFFFE600)
                )
            )
            AddressBar(address = "Avenida Professor Alfonso Bovero 546")

            // Grid de productos
            var products by remember {
                mutableStateOf(
                    listOf(
                        // Producto específico de la IA siempre de primero
                        Product(
                            title = "Adidas Botella Metálica Deportiva",
                            store = "Adidas",
                            price = "$75.900",
                            imageUrl = "https://assets.adidas.com/images/h_2000,f_auto,q_auto,fl_lossy,c_fill,g_auto/0cbba516fdf64c43a5791b100bd259b3_9366/Botella_Metalica_con_Tapon_de_Rosca_de_06_l_Negro_KC6412_01_00_standard.jpg",
                            label = "Recomendado IA",
                            isRecommendedByAI = true
                        )
                    ) + List(19) { generateRandomProduct(initialSearchQuery) }
                )
            }

            // Mostrar productos según el estado
            val displayProducts = if (isAISearchActive) {
                aiSelectedProducts
            } else {
                products
            }

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                contentPadding = PaddingValues(bottom = 16.dp),
                state = rememberLazyGridState().also { state ->
                    LaunchedEffect(state) {
                        snapshotFlow { state.firstVisibleItemIndex }
                            .collect { index ->
                                // Mostrar mensaje de ayuda después de hacer scroll
                                if (index > 5) {
                                    showHelpMessage = true
                                } else {
                                    showHelpMessage = false
                                }
                            }
                    }
                }
            ) {
                items(displayProducts.size) { index ->
                    val product = displayProducts[index]
                    ProductCard(product = product, onClick = {
                        // Navegar a la pantalla de detalle
                        val route = "detail/" +
                                Uri.encode(product.title) + "/" +
                                Uri.encode(product.imageUrl) + "/" +
                                Uri.encode(product.price) + "/" +
                                Uri.encode(product.isRecommendedByAI.toString())
                        navController.navigate(route) {
                            // Evitar múltiples copias de la misma pantalla en el back stack
                            launchSingleTop = true
                            // Restaurar el estado si la pantalla ya existe
                            restoreState = true
                        }
                    })

                    // Cargar más productos cuando se acerca al final (solo si no es búsqueda de IA)
                    if (!isAISearchActive && index >= products.size - 5) {
                        products = products + List(10) { generateRandomProduct(initialSearchQuery) }
                    }
                }
            }
        }

        // Botón flotante arrastrable
        var offsetX by remember { mutableStateOf(0f) }
        var offsetY by remember { mutableStateOf(0f) }
        val density = LocalDensity.current

        // Obtener dimensiones de la pantalla para posicionar inicialmente
        val screenWidth = with(density) { 400.dp.toPx() } // Valor aproximado
        val screenHeight = with(density) { 800.dp.toPx() } // Valor aproximado
        val buttonSize = with(density) { 120.dp.toPx() } // Tamaño aproximado del botón

        // Posicionar inicialmente pegado al borde derecho en la mitad vertical
        LaunchedEffect(Unit) {
            offsetX = screenWidth - buttonSize - 16f // Pegado al borde derecho con padding
            offsetY = (screenHeight - buttonSize) / 2 // Centrado verticalmente
        }

        // Mensaje de ayuda que aparece cuando se hace scroll
        if (showHelpMessage) {
            val helpCardWidth = 180 // en dp
            Card(
                modifier = Modifier
                    .offset {
                        IntOffset(
                            (offsetX - helpCardWidth - 120).roundToInt(), // 120dp de margen
                            helpCardY
                        )
                    }
                    .width(helpCardWidth.dp)
                    .zIndex(1f),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF424242)
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 8.dp
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "¿Necesitas ayuda?",
                    color = Color.White,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(12.dp),
                    textAlign = TextAlign.Center,
                    maxLines = 2
                )
            }
        }

        FloatingActionButton(
            onClick = {
                showChat = true
                showHelpMessage = false
            },
            modifier = Modifier
                .offset { IntOffset(offsetX.roundToInt(), helpCardY) }
                .pointerInput(Unit) {
                    detectDragGestures { change, dragAmount ->
                        change.consume()
                        val newX = offsetX + dragAmount.x
                        val newY = offsetY + dragAmount.y

                        // Límites para que no salga de la pantalla
                        val maxX = screenWidth - buttonSize
                        val maxY = screenHeight - buttonSize

                        // Aplicar límites
                        offsetX = newX.coerceIn(0f, maxX)
                        offsetY = newY.coerceIn(0f, maxY)

                        // Efecto de pegado a bordes
                        val snapThreshold = 50f
                        if (offsetX < snapThreshold) {
                            offsetX = 0f // Pegar al borde izquierdo
                        } else if (offsetX > maxX - snapThreshold) {
                            offsetX = maxX // Pegar al borde derecho
                        }

                        if (offsetY < snapThreshold) {
                            offsetY = 0f // Pegar al borde superior
                        } else if (offsetY > maxY - snapThreshold) {
                            offsetY = maxY // Pegar al borde inferior
                        }
                    }
                },
            containerColor = Color.Transparent,
            elevation = androidx.compose.material3.FloatingActionButtonDefaults.elevation(
                defaultElevation = 6.dp
            ),
            shape = RoundedCornerShape(28.dp)
        ) {
            androidx.compose.material3.Text(
                text = "Buscar con IA",
                color = Color.White,
                fontSize = 14.sp,
                modifier = Modifier
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                Color(0xFF00FFFF), // Cyan
                                Color(0xFF0000FF), // Azul
                                Color(0xFF8000FF)  // Púrpura
                            )
                        ),
                        shape = RoundedCornerShape(20.dp)
                    )
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            )
        }

        // Chat de IA
        if (showChat) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f)),
                contentAlignment = Alignment.Center
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    ),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 8.dp
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "¿Qué estás buscando?",
                            fontSize = 18.sp,
                            color = Color.Black,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )

                        TextField(
                            value = chatMessage,
                            onValueChange = { chatMessage = it },
                            placeholder = {
                                Text("Describe lo que buscas...")
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(120.dp)
                                .padding(bottom = 16.dp)
                                .border(
                                    width = 2.dp,
                                    color = Color(0xFFE0E0E0),
                                    shape = RoundedCornerShape(12.dp)
                                ),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.White,
                                unfocusedContainerColor = Color.White,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            ),
                            shape = RoundedCornerShape(12.dp),
                            textStyle = TextStyle(
                                color = Color.Black,
                                fontSize = 16.sp
                            ),
                            keyboardOptions = KeyboardOptions.Default.copy(
                                imeAction = ImeAction.Send
                            ),
                            keyboardActions = KeyboardActions(
                                onSend = {
                                    if (chatMessage.isNotBlank()) {
                                        // Generar productos específicos sin repeticiones
                                        aiSelectedProducts = generateAISelectedProducts(chatMessage)
                                        isAISearchActive = true
                                        showChat = false
                                        chatMessage = ""
                                    }
                                }
                            )
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            Button(
                                onClick = {
                                    if (chatMessage.isNotBlank()) {
                                        // Generar productos específicos sin repeticiones
                                        aiSelectedProducts = generateAISelectedProducts(chatMessage)
                                        isAISearchActive = true
                                        showChat = false
                                        chatMessage = ""
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.Transparent
                                ),
                                modifier = Modifier
                                    .background(
                                        brush = Brush.horizontalGradient(
                                            colors = listOf(
                                                Color(0xFF00FFFF), // Cyan
                                                Color(0xFF0000FF), // Azul
                                                Color(0xFF8000FF)  // Púrpura
                                            )
                                        ),
                                        shape = RoundedCornerShape(8.dp)
                                    )
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = "Buscar",
                                    tint = Color.White
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Buscar", color = Color.White)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ProductCard(product: Product, onClick: (() -> Unit)? = null) {
    Box(
        modifier = Modifier
            .padding(8.dp)
            .background(Color.White, RoundedCornerShape(12.dp))
            .then(
                if (product.isRecommendedByAI) {
                    Modifier.border(
                        width = 2.dp,
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                Color(0xFF00FFFF), // Cyan
                                Color(0xFF0000FF), // Azul
                                Color(0xFF8000FF)  // Púrpura
                            )
                        ),
                        shape = RoundedCornerShape(12.dp)
                    )
                } else {
                    Modifier
                }
            )
            .fillMaxWidth()
            .aspectRatio(if (product.isRecommendedByAI) 0.7f else 0.8f)
            .clickable { onClick?.invoke() }
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            // Imagen desde URL usando Coil
            AsyncImage(
                model = product.imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Fit
            )
            if (product.label != null) {
                Text(
                    text = product.label,
                    color = Color(0xFF009EE3),
                    fontSize = 12.sp,
                    modifier = Modifier.padding(top = 4.dp, bottom = 2.dp)
                )
            }
            Text(
                text = product.title,
                color = Color.Black,
                fontSize = 14.sp,
                maxLines = 2,
                modifier = Modifier.padding(top = 4.dp)
            )
            Text(
                text = product.price,
                color = Color.Black,
                fontSize = 18.sp, // Precio más grande que el título (14sp)
                modifier = Modifier.padding(top = 4.dp),
                maxLines = 1
            )
            // "Llega hoy" solo aparece aleatoriamente en 30% de las cards
            if ((0..100).random() < 30) {
                Text(
                    text = "Llega hoy",
                    color = Color(0xFF00A650),
                    fontSize = 12.sp,
                    modifier = Modifier.padding(top = 2.dp)
                )
            }
        }
    }
}

@Composable
fun AddressBar(address: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFFFE600))
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.LocationOn,
            contentDescription = "Ubicación",
            tint = Color.Black,
            modifier = Modifier.size(18.dp)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = address,
            color = Color.Black,
            fontSize = 15.sp
        )
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ExperimentoDesignThinkingTheme {
        Greeting("Android")
    }
}

// Función para generar productos específicos seleccionados por IA sin repeticiones
fun generateAISelectedProducts(query: String): List<Product> {
    val selectedProducts = mutableListOf<Product>()
    val usedTitles = mutableSetOf<String>()
    val lowerQuery = query.lowercase()
    
    // Obtener el producto principal sugerido por la IA
    val mainAIProduct = baseProducts.firstOrNull { it.label == "Deportivo" } ?: baseProducts.first()
    val mainPrice = mainAIProduct.price.replace("$", "").replace(",", "").toIntOrNull() ?: 75000
    val mainTitle = mainAIProduct.title
    
    val filtered = if (lowerQuery.contains("depor")) {
        baseProducts.filter { it.label.equals("Deportivo", ignoreCase = true) }
    } else {
        baseProducts
    }
    
    for (product in filtered) {
        if (!usedTitles.contains(product.title) && selectedProducts.size < 10) {
            val productPrice = product.price.replace("$", "").replace(",", "").toIntOrNull() ?: 0
            
            // Si la bandera está habilitada, verificar que no sea el mismo producto con menor precio
            val shouldInclude = if (aiPriceControlEnabled) {
                // Si es el mismo producto que el principal, solo incluirlo si tiene precio >= al principal
                if (product.title == mainTitle) {
                    productPrice >= mainPrice
                } else {
                    // Si es un producto diferente, incluirlo sin restricción de precio
                    true
                }
            } else {
                true // Sin restricción de precio
            }
            
            if (shouldInclude) {
                usedTitles.add(product.title)
                selectedProducts.add(
                    product.copy(
                        label = "Sugerencia IA",
                        isRecommendedByAI = true
                    )
                )
            }
        }
    }
    
    // Si no hay suficientes productos, agregar algunos más (pero respetando la restricción)
    if (selectedProducts.size < 5) {
        val remainingProducts = filtered.filter { product ->
            !usedTitles.contains(product.title) && 
            !selectedProducts.any { it.title == product.title }
        }
        
        for (product in remainingProducts.take(5 - selectedProducts.size)) {
            val productPrice = product.price.replace("$", "").replace(",", "").toIntOrNull() ?: 0
            
            // Aplicar la misma lógica de restricción
            val shouldInclude = if (aiPriceControlEnabled) {
                if (product.title == mainTitle) {
                    productPrice >= mainPrice
                } else {
                    true
                }
            } else {
                true
            }
            
            if (shouldInclude) {
                usedTitles.add(product.title)
                selectedProducts.add(
                    product.copy(
                        label = "Sugerencia IA",
                        isRecommendedByAI = true
                    )
                )
            }
        }
    }
    
    return selectedProducts
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    title: String,
    imageUrl: String,
    price: String,
    navController: androidx.navigation.NavHostController,
    isRecommendedByAI: Boolean = true
) {
    var searchText by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }
    var selectedSize by remember { mutableStateOf<String?>(null) }
    val focusManager = LocalFocusManager.current
    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    title = {
                        TextField(
                            value = searchText,
                            onValueChange = { searchText = it },
                            placeholder = {
                                Text(
                                    "Buscar en Mercado Libre",
                                    color = Color.Gray,
                                    fontSize = 18.sp
                                )
                            },
                            singleLine = true,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.White,
                                unfocusedContainerColor = Color.White,
                                disabledContainerColor = Color.White,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent
                            ),
                            shape = RoundedCornerShape(24.dp),
                            textStyle = TextStyle(
                                color = Color.Black,
                                fontSize = 18.sp
                            ),
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = "Buscar",
                                    tint = Color.Gray,
                                    modifier = Modifier.size(24.dp)
                                )
                            },
                            keyboardOptions = KeyboardOptions.Default.copy(
                                imeAction = ImeAction.Search
                            ),
                            keyboardActions = KeyboardActions(
                                onSearch = {
                                    if (searchText.isNotBlank()) {
                                        focusManager.clearFocus()
                                    }
                                }
                            )
                        )
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                if (navController.previousBackStackEntry != null) {
                                    navController.popBackStack()
                                } else {
                                    navController.navigate("results") {
                                        popUpTo("results") { inclusive = true }
                                    }
                                }
                            }
                        ) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Atrás")
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color(0xFFFFE600)
                    )
                )
                AddressBar(address = "Avenida Professor Alfonso Bovero 546")
            }
        },
        containerColor = Color(0xFFF5F5F5)
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(16.dp)
        ) {
            item {
                if (isRecommendedByAI) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .background(Color(0xFFFF6F00), RoundedCornerShape(4.dp))
                                .padding(horizontal = 8.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = "MÁS VENDIDO",
                                color = Color.White,
                                fontSize = 13.sp,
                                letterSpacing = 1.sp
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "1° en Termos",
                            color = Color(0xFF009EE3),
                            fontSize = 13.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
                // Nuevo | +1000 vendidos y estrellas
                Text(
                    text = "Nuevo  |  +1000 vendidos",
                    color = Color(0xFF888888),
                    fontSize = 12.sp,
                    modifier = Modifier.padding(bottom = 2.dp)
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("4.7", color = Color(0xFF666666), fontSize = 12.sp)
                    Spacer(modifier = Modifier.width(2.dp))
                    repeat(4) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            tint = Color(0xFF009EE3),
                            modifier = Modifier.size(14.dp)
                        )
                    }
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = Color(0xFF009EE3),
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text("(106)", color = Color(0xFF666666), fontSize = 12.sp)
                }
                Spacer(modifier = Modifier.height(8.dp))
                AsyncImage(
                    model = imageUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = title,
                    fontSize = 20.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = "Talla:",
                    fontSize = 16.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    listOf("S", "M", "L", "XL").forEach { size ->
                        val isSelected = selectedSize == size
                        Button(
                            onClick = { selectedSize = size },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (isSelected) Color(0xFF009EE3) else Color.White
                            ),
                            border = BorderStroke(
                                width = if (isSelected) 2.dp else 1.dp,
                                color = if (isSelected) Color(0xFF009EE3) else Color.Gray
                            ),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.height(36.dp)
                        ) {
                            Text(
                                size, 
                                color = if (isSelected) Color.White else Color.Black,
                                fontWeight = if (isSelected) androidx.compose.ui.text.font.FontWeight.Bold else androidx.compose.ui.text.font.FontWeight.Normal
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(32.dp))
            }
            item {
                Text(
                    text = price,
                    fontSize = 28.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                Button(
                    onClick = { showError = true },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF009EE3)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Comprar ahora", color = Color.White, fontSize = 18.sp)
                }
            }
        }
        // Diálogo de error
        if (showError) {
            androidx.compose.material3.AlertDialog(
                onDismissRequest = { showError = false },
                title = {
                    Text(
                        text = "Error del Servidor",
                        color = Color.Red,
                        fontSize = 18.sp,
                        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                    )
                },
                text = {
                    Text(
                        text = "Ha ocurrido un error en el servidor. Por favor, inténtalo de nuevo más tarde.",
                        color = Color.Black,
                        fontSize = 16.sp
                    )
                },
                confirmButton = {
                    Button(
                        onClick = { showError = false },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF009EE3))
                    ) {
                        Text("Aceptar", color = Color.White)
                    }
                },
                containerColor = Color.White,
                shape = RoundedCornerShape(16.dp)
            )
        }
    }
}