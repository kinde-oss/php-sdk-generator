# Kinde PHP generator

The generator for the [Kinde PHP SDK](https://github.com/kinde-oss/kinde-php-sdk).

[![PRs Welcome](https://img.shields.io/badge/PRs-welcome-brightgreen.svg?style=flat-square)](https://makeapullrequest.com) [![Kinde Docs](https://img.shields.io/badge/Kinde-Docs-eee?style=flat-square)](https://kinde.com/docs/developer-tools) [![Kinde Community](https://img.shields.io/badge/Kinde-Community-eee?style=flat-square)](https://thekindecommunity.slack.com)

## Overview

This generator creates an SDK in PHP that can authenticate to Kinde using the Authorization Code grant or the Authorization Code with PKCE grant via the [OAuth 2.0 protocol](https://oauth.net/2/). It can also access the [Kinde Management API](https://kinde.com/api/docs/#kinde-management-api) using the client credentials grant.

Also, see the SDKs section in Kinde’s [contributing guidelines](https://github.com/kinde-oss/.github/blob/main/.github/CONTRIBUTING.md).

## Usage

### Requirements

You will need the following tools to be able to generate the SDK.

#### GNU Make

GNU Make is installed by default on most Linux distributions and MacOS, if however it is not available on your operating system, please consult the documentation [here](https://www.gnu.org/software/make/).

### Initial set up

1. Clone the repository to your machine:

   ```bash
   git clone https://github.com/kinde-oss/php-sdk-generator.git
   ```

2. Go into the project:

   ```bash
   cd ./php-sdk-generator
   ```

3. Install the OpenAPI Generator tool:

   https://openapi-generator.tech/docs/installation

### SDK generation

Run the following command to generate the SDK:

```bash
make build-php
```

Please be sure to run `make clean` prior to running the above command, to clean up any generated SDKs. In addition please change your business name in `kinde-mgmt-api-specs.yaml` before generating the SDK.

```
...
servers:
  - url: https://{businessName}.kinde.com
    variables:
      businessName:
        description: Business Name created in the Kinde Console
        default: YOUR_BUSINESS_NAME // <-- Change here
...
```

**Note:** The API specifications should always point to Kinde's hosted version: https://kinde.com/api/kinde-mgmt-api-specs.yaml. Please ensure that this file is the same as that of the hosted version before generating the SDK.

The SDK gets outputted to: `./out/kinde-php-sdk`, which you can enter via:

```bash
cd ./out/kinde-php-sdk
```

## SDK documentation

[PHP SDK](https://kinde.com/docs/developer-tools/php-sdk)

## Development

The instructions provided in the "Usage" section above are sufficient to get you started.

## Contributing

Please refer to Kinde’s [contributing guidelines](https://github.com/kinde-oss/.github/blob/489e2ca9c3307c2b2e098a885e22f2239116394a/CONTRIBUTING.md).

## License

By contributing to Kinde, you agree that your contributions will be licensed under its MIT License.
