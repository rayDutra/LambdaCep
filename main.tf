provider "aws" {
    region = "sa-east-1"
    access_key = "teste"
    secret_key = "teste"
    skip_credentials_validation = true
    skip_requesting_account_id = true
    skip_metadata_api_check = true

    endpoints {
        dynamodb = "http://localhost:4566"
        ec2 = "http://localhost:4566"
        lambda = "http://localhost:4566"
    }
}

resource "aws_iam_role" "lambda_exec" {
    name = "lambda-exec-role"

    assume_role_policy = jsonencode({
        Version   = "2012-10-17"
        Statement = [
            {
                Effect    = "Allow"
                Principal = {
                    Service = "lambda.amazonaws.com"
                }
                Action    = "sts:AssumeRole"
            }
        ]
    })
}

resource "aws_lambda_function" "cep_lambda" {
    filename      = "target/ProjectCep-0.0.1-SNAPSHOT.jar"
    function_name = "cepInfoLambda"
    role          = aws_iam_role.lambda_exec.arn
    handler       = "com.example.CepInfoLambda::handleRequest"
    runtime       = "java11"
}
