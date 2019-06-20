package webservices.fault;

public class ServiceFaultInfo {
    private String message;
    private FaultMessage expression;

    public ServiceFaultInfo(FaultMessage expression, Object ...args ){
        setMessage(String.format(expression.getMessageExpression(), args));
    }
    public ServiceFaultInfo(FaultMessage expression){
        this.expression = expression;

    }
    public String getMessage() {
        return message;
    }

     public void setMessage(String message) {
        this.message = message;
    }

    public FaultMessage getExpression() {
        return expression;
    }

    public void setExpression(FaultMessage expression) {
        this.expression = expression;
    }
}
