import 'package:buscam/screens/signin_screen.dart';
import 'package:buscam/screens/signup_screen.dart';
import 'package:buscam/theme/theme.dart';
import 'package:buscam/widgets/custom_scaffold.dart';
import 'package:buscam/widgets/welcome_button.dart';
import 'package:flutter/material.dart';

class WelcomeScreen extends StatelessWidget {
  const WelcomeScreen({super.key});

  @override
  Widget build(BuildContext context) {
    return CustomScaffold(
      child: Column(
        children: [
          Flexible(
              flex: 2,
              child: Container(
                padding: const EdgeInsets.symmetric(
                  vertical: 0,
                  horizontal: 40.0,
                ),
                child: Center(
                  child: RichText(
                    textAlign: TextAlign.center,
                    text: const TextSpan(
                      children: [
                        TextSpan(
                            text: 'Bienvenido a Buscam\n',
                            style: TextStyle(
                              fontSize: 40.0,
                              fontWeight: FontWeight.w600,
                            )),
                        TextSpan(
                            text:
                                '\nInicia sesión o crea una cuenta para buscar el producto que necesitas',
                            style: TextStyle(
                              fontSize: 17,
                              // height: 0,
                            ))
                      ],
                    ),
                  ),
                ),
              )),
            Flexible(
              flex: 1,
              child: Align(
                alignment: Alignment.bottomRight,
                child: Row(
                  children: [
                    const Expanded(
                      child: WelconeButton(
                        buttonText: 'Iniciar sesión',
                        onTap: SigninScreen(),
                        color: Colors.transparent ,
                        textColor: Colors.white,
                      ),
                    ),
                    Expanded(
                      child: WelconeButton(
                        buttonText: 'Registrarse',
                        onTap: const SignupScreen(),
                        color: Colors.white ,
                        textColor: lightColorScheme.primary,
                      ),
                    )
                    
                  ],
                ),
              ))
        ],
      ),
    );
  }
}
