package Cliente;

import Inicio.ConexionBD;
import Pastelero.Consulta;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LoggedIn extends ConexionBD
{ 
    //Variables de instancia
    private String firstName;
    private String lastName;
    private String id;
    

    //Constructor
    public LoggedIn()
    {
    }

    //Interfaz de inicio de sesion
    public void start() throws SQLException
    {
        int opcion;
        Scanner op = new Scanner(System.in);
        String opcion2;

        do
        {
            System.out.println("Inicio de sesion exitoso!");
            System.out.println("******************************************");
            System.out.println("Bienvenido de vuelta "+this.firstName+" "+this.lastName);
            System.out.println("1. Hacer pedido");
            System.out.println("2. Consultar pedido");
            System.out.println("3. Consultar calificaciones realizadas");
            System.out.println("Para cerrar sesion presione cualquier otra tecla");
            System.out.println("******************************************");

            System.out.println("Introduzca el numero de la opcion que prefiera:");
            opcion = op.nextInt();

            Consulta obj = new Consulta();
            Transferencia tf = new Transferencia();

            switch(opcion)
            {
                case 1:
                    int idPan;
                    String productos;
                    System.out.println("Tenemos los siguientes pasteleros disponibles:");
                    obj.pasteleroDisponible();
                    System.out.println("******************************************");
                    System.out.println("Seleccione el panadero indicando su ID:");
                    System.out.println("******************************************");
                    idPan = op.nextInt();
                    System.out.println("******************************************");
                    System.out.println("Productos disponibles");
                    System.out.println("******************************************");
                    obj.pasteleroEscogido(idPan);
                    op.nextLine();
                    System.out.println("******************************************");
                    System.out.println("Seleccione los productos para su pedido indicando su nombre y separandolos por comillas:");
                    productos = op.nextLine();
                    System.out.println("******************************************");
                    System.out.println("Productos escogidos para pedido realizado!!!!");
                    Consult cs=new Consult();
                    float monto=obj.retornarMonto(productos);
                    cs.guardarPedido(monto, idPan, this.id);
                    tf.realizarTransferencia();
                    
                    
                        
                    
                    
                    
                break;
                case 2:
                    System.out.println("Sus pedidos son:");
                    Consult cons = new Consult();
                    cons.realizarConsulta(this.id);

                    String selection;

                    selection = op.nextLine();
                    System.out.println("Para cancelar un pedido presione 1");
                    System.out.println("Para regresar presione otra tecla");
                    selection = op.nextLine();

                    while(selection.equals("1"))
                    {
                        System.out.println("Ingrese el numero de la orden que desea cancelar");
                        String numeroo = op.nextLine();
                        int numero = Integer.parseInt(numeroo);

                        cons.cancelarPedido(numero);

                        System.out.println("Para cancelar otro pedido presione 1");
                        System.out.println("Para regresar presione otra tecla");
                        selection = op.nextLine();
                    }
                    
                    System.out.print("Escriba 1 si quiere calificar algun pedido: ");
                    opcion2=op.nextLine();
                        if (opcion2.equals("1")){
                        String pedido="";
                        boolean condi=true;
                        do{
                        try{
                        System.out.print("Ingrese el id del pedido que quiere calificar: ");
                        pedido =op.nextLine();
                        int parse_numero = Integer.parseInt(pedido);
                        condi=false;
                        }catch(Exception e){
                        System.out.println(pedido + " no es un id valido");
                        }
                        }while(condi);
                        System.out.print("Ingrese calificacion de 1 al 5: ");
                        String calificacion=op.nextLine();
                        System.out.print("Nivel de Satisfaccion: ");
                        String satisfaccion=op.nextLine();
                        System.out.print("??Se lo recomendaria a tus amigos? Si o No: ");
                        String recomendacion=op.nextLine();
                        System.out.print("Algun recomendacion: ");
                        String recomendacionC=op.nextLine();
                        System.out.print("Algun comentario extra: ");
                        String comentario=op.nextLine();
                        
                        cons.califacar(pedido, calificacion, satisfaccion, recomendacion, recomendacionC, comentario);
            }
                break;
                case 3:
                    System.out.println("Sus consultas realizadas son");
                    Consult cons2 = new Consult();
                    cons2.consultarCalificacion(id);
                    break;
            }
        }while(opcion < 4 & opcion > 0);
    }

    //Metodo para validar si los datos coinciden
    public boolean validate(String id, String name)
    {
        PreparedStatement pst = null;
        ResultSet rst = null;

        try
        {
            String ask = "SELECT * FROM cliente";
            pst = Conectar().prepareStatement(ask);
            rst = pst.executeQuery();
            
            while(rst.next())
            {
                if(rst.getString(1).equals(id) & rst.getString(2).equals(name))
                {
                    this.firstName = rst.getString(2);
                    this.lastName = rst.getString(3);
                    this.id = rst.getString(1);
                    return true;
                }
            }
        }
        catch (Exception e)
        {
            System.out.println("Error en la comunicacion -> " + e);
        }

        return false;
    }
    
    public String getID()
    {
        return this.id;
    }

}
