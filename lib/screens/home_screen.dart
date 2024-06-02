import 'package:buscam/theme/theme.dart';
import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';

class HomeScreen extends StatelessWidget {
  const HomeScreen({Key? key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      bottomNavigationBar: BottomAppBar(
      height: 70,
      color: Colors.white,  
      child: Row(
        mainAxisAlignment: MainAxisAlignment.spaceBetween,
        children: [
        Column(
          children: [
            Icon(Icons.home_rounded),
            Text('Inicio')
          ],
        ),
         Column(
          children: [
            Icon(Icons.hexagon_rounded),
            Text('Inicio')
          ],
        ),
         Column(
          children: [
            Icon(Icons.home_rounded),
            Text('Inicio')
          ],
        ),
         Column(
          children: [
            Icon(Icons.camera),
            Text('Inicio')
          ],
        ),
         Column(
          children: [
            Icon(Icons.message_outlined),
            Text('Inicio')
          ],
        ),
 
      ],),),
      backgroundColor: Color.fromARGB(255, 226, 224, 224),
      body: Column(
        children: [
          Column(
            children: [
              const Padding(
                padding: EdgeInsets.only(top: 60.0, left: 11, right: 11),
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.spaceBetween,
                  children: [
                    Expanded(
                      child: Row(
                        children: [
                          Image(image: AssetImage('assets/images/logo.png')),
                        ],
                      ),
                    ),
                    Badge(
                      label: Text('9+'),
                      child: Image(
                        height: 40,
                        width: 30,
                        image: AssetImage('assets/icons/bell.png'),
                      ),
                    ),
                    SizedBox(
                      width: 10,
                    ),
                    Image(
                      height: 40,
                      width: 30,
                      image: AssetImage('assets/icons/map.png'),
                    ),
                  ],
                ),
              ),
              const SizedBox(
                height: 20,
              ),
              Container(
                height: 45,
                width: 375,
                child: TextField(
                  decoration: InputDecoration(
                    prefixIcon: Icon(
                      Icons.search,
                      color: Colors.grey,
                    ),
                    hintText: "buscar..",
                    hintStyle: const TextStyle(
                      fontSize: 20,
                      color: Colors.grey,
                      fontWeight: FontWeight.normal,
                    ),
                    border: OutlineInputBorder(
                      borderRadius: BorderRadius.circular(10),
                    ),
                  ),
                ),
              ),
              SizedBox(
                height: 15,
              ),
              Padding(
                padding: const EdgeInsets.only(left: 8.0),
                child: Row(
                  children: [
                    Text(
                      'Categorías',
                      style: TextStyle(
                          fontSize: 20,
                          color: Colors.black,
                          fontWeight: FontWeight.bold),
                    )
                  ],
                ),
              ),
              Padding(
                padding: const EdgeInsets.only(top: 5.0),
                child: Row(
                  children: [
                    Container(
                      width: 390,
                      height: 100,
                      color: lightColorScheme.onPrimary,
                      child: Padding(
                        padding: const EdgeInsets.only(right: 8.0),
                        child: Row(
                          mainAxisAlignment: MainAxisAlignment.spaceBetween,
                          children: [
                            Padding(
                              padding: const EdgeInsets.only(left: 8),
                              child: Column(
                                mainAxisAlignment: MainAxisAlignment.center,
                                crossAxisAlignment: CrossAxisAlignment.center,
                                children: [
                                  Container(
                                    height: 40,
                                    width: 40,
                                    child: Container(
                                      decoration: BoxDecoration(
                                        shape: BoxShape.circle,
                                        border: Border.all(
                                          color: lightColorScheme.primary,
                                          width: 2.0,
                                        ),
                                      ),
                                      child: const ClipOval(
                                        child: Image(
                                            image: AssetImage(
                                                'assets/icons/grid.png')),
                                      ),
                                    ),
                                  ),
                                  Text('Todos'),
                                  Container(
                                    width: 45,
                                    height: 2,
                                    color: Colors.black,
                                  ),
                                ],
                              ),
                            ),
                            Padding(
                              padding: const EdgeInsets.only(left: 8),
                              child: Column(
                                mainAxisAlignment: MainAxisAlignment.center,
                                crossAxisAlignment: CrossAxisAlignment.center,
                                children: [
                                  Container(
                                    height: 40,
                                    width: 40,
                                    child: Container(
                                      decoration: BoxDecoration(
                                        shape: BoxShape.circle,
                                        border: Border.all(
                                          color: lightColorScheme.primary,
                                          width: 2.0,
                                        ),
                                      ),
                                      child: const ClipOval(
                                        child: Image(
                                            image: AssetImage(
                                                'assets/icons/phone.png')),
                                      ),
                                    ),
                                  ),
                                  SizedBox(
                                    height: 4,
                                  ),
                                  Text('Teléfonos')
                                ],
                              ),
                            ),
                            Padding(
                              padding: const EdgeInsets.only(left: 8),
                              child: Column(
                                mainAxisAlignment: MainAxisAlignment.center,
                                crossAxisAlignment: CrossAxisAlignment.center,
                                children: [
                                  Container(
                                    height: 40,
                                    width: 40,
                                    child: Container(
                                      decoration: BoxDecoration(
                                        shape: BoxShape.circle,
                                        border: Border.all(
                                          color: lightColorScheme.primary,
                                          width: 2.0,
                                        ),
                                      ),
                                      child: const ClipOval(
                                        child: Image(
                                            image: AssetImage(
                                                'assets/icons/clotes.png')),
                                      ),
                                    ),
                                  ),
                                  SizedBox(
                                    height: 4,
                                  ),
                                  Text('Ropa')
                                ],
                              ),
                            ),
                            Padding(
                              padding: const EdgeInsets.only(left: 8),
                              child: Column(
                                mainAxisAlignment: MainAxisAlignment.center,
                                crossAxisAlignment: CrossAxisAlignment.center,
                                children: [
                                  Container(
                                    height: 40,
                                    width: 40,
                                    child: Container(
                                      decoration: BoxDecoration(
                                        shape: BoxShape.circle,
                                        border: Border.all(
                                          color: lightColorScheme.primary,
                                          width: 2.0,
                                        ),
                                      ),
                                      child: const ClipOval(
                                        child: Image(
                                            image: AssetImage(
                                                'assets/icons/computer.png')),
                                      ),
                                    ),
                                  ),
                                  SizedBox(
                                    height: 4,
                                  ),
                                  Text('Ordenadores')
                                ],
                              ),
                            ),
                            Padding(
                              padding: const EdgeInsets.only(left: 8),
                              child: Column(
                                mainAxisAlignment: MainAxisAlignment.center,
                                crossAxisAlignment: CrossAxisAlignment.center,
                                children: [
                                  Container(
                                    height: 40,
                                    width: 40,
                                    child: Container(
                                      decoration: BoxDecoration(
                                        shape: BoxShape.circle,
                                        border: Border.all(
                                          color: lightColorScheme.primary,
                                          width: 2.0,
                                        ),
                                      ),
                                      child: const ClipOval(
                                        child: Image(
                                            image: AssetImage(
                                                'assets/icons/shoes.png')),
                                      ),
                                    ),
                                  ),
                                  SizedBox(
                                    height: 4,
                                  ),
                                  Text('Calzado')
                                ],
                              ),
                            ),
                          ],
                        ),
                      ),
                    ),
                  ],
                ),
              ),
              Padding(
                padding: EdgeInsets.only(right: 1),
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.spaceBetween,
                  children: [
                    Card(
                      clipBehavior: Clip.antiAlias,
                      elevation: 0,
                      margin: EdgeInsets.only(top: 1.0),
                      color: lightColorScheme.primary,
                      shape: RoundedRectangleBorder(
                        side: BorderSide(
                          color: Colors.black38,
                          width: 1.0
                          
                        ),
                        borderRadius: BorderRadius.circular(12),
                      ),
                      child: Container(
                        height: 288,
                        width: 194,
                        child: Stack(
                          
                        ),
                      ),
                    )
                    
                    
                  ],
                ),
              ),
            ],
          ),
        ],
      ),
    );
  }
}
