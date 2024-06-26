// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: Apache-2.0
package aws.example.iam;

import com.amazonaws.services.identitymanagement.AmazonIdentityManagement;
import com.amazonaws.services.identitymanagement.AmazonIdentityManagementClientBuilder;
import com.amazonaws.services.identitymanagement.model.CreatePolicyRequest;
import com.amazonaws.services.identitymanagement.model.CreatePolicyResult;

/**
 * Creates a fixed policy with a provided policy name.
 */
public class CreatePolicy {

        public static final String POLICY_DOCUMENT = "{" +
                        "  \"Version\": \"2012-10-17\"," +
                        "  \"Statement\": [" +
                        "    {" +
                        "        \"Effect\": \"Allow\"," +
                        "        \"Action\": \"logs:CreateLogGroup\"," +
                        "        \"Resource\": \"%s\"" +
                        "    }," +
                        "    {" +
                        "        \"Effect\": \"Allow\"," +
                        "        \"Action\": [" +
                        "            \"dynamodb:DeleteItem\"," +
                        "            \"dynamodb:GetItem\"," +
                        "            \"dynamodb:PutItem\"," +
                        "            \"dynamodb:Scan\"," +
                        "            \"dynamodb:UpdateItem\"" +
                        "       ]," +
                        "       \"Resource\": \"RESOURCE_ARN\"" +
                        "    }" +
                        "   ]" +
                        "}";

        public static void main(String[] args) {

                final String USAGE = "To run this example, supply a policy name\n" +
                                "Ex: CreatePolicy <policy-name>\n";

                if (args.length != 1) {
                        System.out.println(USAGE);
                        System.exit(1);
                }

                String policy_name = args[0];

                final AmazonIdentityManagement iam = AmazonIdentityManagementClientBuilder.defaultClient();

                CreatePolicyRequest request = new CreatePolicyRequest()
                                .withPolicyName(policy_name)
                                .withPolicyDocument(POLICY_DOCUMENT);

                CreatePolicyResult response = iam.createPolicy(request);

                System.out.println("Successfully created policy: " +
                                response.getPolicy().getPolicyName());
        }
}
