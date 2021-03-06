package lp3.college.gui.fx;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lp3.college.dao.CursoDAO;
import lp3.college.entidades.Curso;
import lp3.college.infra.Database;

public class CursoFormController implements Initializable {
    @FXML
    private TextField textFieldCodigo;
    @FXML
    private TextField textFieldNome;
    @FXML
    private Button buttonNovo;
    @FXML
    private Button buttonAbrir;
    @FXML
    private Button buttonSalvar;
    @FXML
    private Button buttonDeletar;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image novoIcon = new Image(getClass().getResourceAsStream("images/new doc.png"));
        Image openIcon = new Image(getClass().getResourceAsStream("images/open doc.png"));
        Image SaveIcon = new Image(getClass().getResourceAsStream("images/save doc.png"));
        Image DelIcon = new Image(getClass().getResourceAsStream("images/delete doc.png"));

        buttonNovo.setGraphic(new ImageView(novoIcon));
        buttonAbrir.setGraphic(new ImageView(openIcon));
        buttonSalvar.setGraphic(new ImageView(SaveIcon));
        buttonDeletar.setGraphic(new ImageView(DelIcon));
    }

    public void acaoNovo() {
        textFieldCodigo.clear();
        textFieldNome.clear();
    }

    public void acaoAbrir() {
        String codigo = textFieldCodigo.getText();
        String nome = textFieldNome.getText();
        Curso c;
        CursoDAO dao = new CursoDAO(Database.getConexao());

        if (!codigo.isEmpty()) {
            c = dao.buscaPorCodigo(codigo);
        } else if (!nome.isEmpty()) {
            c = dao.buscaPorNome(nome);
        } else {
            System.out.println("Tudo vazio");
            return;
        }

        textFieldCodigo.setText(c.getCodigo());
        textFieldNome.setText(c.getNome());
    }

    public void acaoSalvar() {
        CursoDAO dao = new CursoDAO(Database.getConexao());
        String codigo = textFieldCodigo.getText();
        String nome = textFieldNome.getText();
        Curso p = new Curso(codigo, nome);
        int id = dao.existe(p);
        
        if(id <= 0){
            dao.salva(p);
        } else {
            p.setId(id);
            dao.atualiza(p);
        }
    }

    public void acaoDeletar() {
        String codigo = textFieldCodigo.getText();
        String nome = textFieldNome.getText();
        Curso p = new Curso(codigo, nome);
        CursoDAO dao = new CursoDAO(Database.getConexao());
        dao.deleta(p);
        acaoNovo();
    }
}