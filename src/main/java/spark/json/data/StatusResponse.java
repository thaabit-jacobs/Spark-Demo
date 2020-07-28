package spark.json.data;

public enum StatusResponse 
{
    ERROR("Error"),
    SUCCESS("Success");

    private String status;

    StatusResponse(String status) 
    {
        this.status = status;
    }

    public String getStatus()
    {
        return status;
    }
}