package com.openclassrooms.realestatemanager.repository

/*class LocationRepositoryTest() {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var locationRepository: LocationRepository

    private val locationClient = mock<LocationClient> {
        onBlocking { getLocation(address) } doReturn locationResponse
    }

    @Before
    fun setUp() {
        locationRepository = LocationRepository(locationClient)
    }

    @Test
    fun testCheckAddressShouldReturnValidValues() {
        runBlocking {
            assertEquals(locationResponse, locationRepository.checkAddress(address))
        }
    }
}*/