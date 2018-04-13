# africastalking-scala

## Usage

The SDK needs to be configured with your app username and API key, which you get from the [dashboard](https://account.africastalking.com).

> You can use this SDK for either production or sandbox apps. For sandbox, the app username is **ALWAYS** `sandbox`

## Services
All methods are asynchronous (i.e. will not block current thread Future based) 

All phone numbers use the international format. e.g. `+234xxxxxxxx`.

All **amount strings** contain currency code as well. e.g. `UGX 443.88`.

### `ApplicationService`

- `fetchApplicationData()`: Get app information. e.g. balance

### `AirtimeService`

- `send(String phoneNumber, String amount)`: Send airtime to a phone number. Example amount would be `KES 150`.

For more information about status notification, please read [http://docs.africastalking.com/airtime/callback](http://docs.africastalking.com/airtime/callback)

### `SmsService`

- `send(String message, String[] recipients)`: Send a message

- `sendPremium(String message, String keyword, String linkId, String[] recipients)`: Send a premium SMS

- `fetchMessages()`: Fetch your messages

- `fetchSubscription(String shortCode, String keyword)`: Fetch your premium subscription data

- `createSubscription(String shortCode, String keyword, String phoneNumber, String checkoutToken)`: Create a premium subscription

- `deleteSubscription(String shortCode, String keyword, String phoneNumber)`: Remove a phone number from a premium subscription

For more information on: 

- How to receive SMS: [http://docs.africastalking.com/sms/callback](http://docs.africastalking.com/sms/callback)
- How to get notified of delivery reports: [http://docs.africastalking.com/sms/deliveryreports](http://docs.africastalking.com/sms/deliveryreports)
- How to listen for subscription notifications: [http://docs.africastalking.com/subscriptions/callback](http://docs.africastalking.com/subscriptions/callback)

### `PaymentService`

- `cardCheckout(String productName, String amount, PaymentCard paymentCard, String narration, Map metadata)`: Initiate card checkout.

- `validateCardCheckout(String transactionId, String otp)`: Validate a card checkout

- `bankCheckout(String productName, String amount, BankAccount bankAccount, String narration, Map metadata)`: Initiate bank checkout.

- `validateBankCheckout(String transactionId, String otp)`: Validate a bank checkout

- `bankTransfer(String productName, List<Bank> recipients)`: Move money form payment wallet to bank account

- `mobileCheckout(String productName, String phoneNumber, String amount)`: Initiate mobile checkout.

- `mobileB2C(String productName, List<Consumer> consumers)`: Send mobile money to consumer. 

- `mobileB2B(String productName, Business recipient)`: Send mobile money to business.

- `walletTransfer(String productName, long targetProductCode, String amount, HashMap<String, String> metadata)`: Move money form one payment product to another.

- `topupStash(String productName, String amount, HashMap<String, String> metadata)`: Move money from payment product to app's stash.


For more information, please read [http://docs.africastalking.com/payments](http://docs.africastalking.com/payments)


### `VoiceService`

- `call(String phoneNumber)`: Initiate a phone call

- `fetchQueuedCalls(String phoneNumber)`: Get queued calls

- `uploadMediaFile(String phoneNumber, String url)`: Upload voice media file

- `ActionBuilder`: Build voice xml when callback URL receives a `POST` from Africa's Talking

    - `say()`: Add a `Say` action.

    - `play()`: Add a `Play` action.

    - `getDigits()`: Add a `GetDigits` action.

    - `dial()`: Add a `Dial` action.

    - `conference()`: Add a `Conferemce` action.

    - `record()`: Add a `Record` action.

    - `enqueue()`: Add a `Enqueue` action.

    - `dequeue()`: Add a `Dequeue` action.

    - `reject()`: Add a `Reject` action.

    - `redirect()`: Add a `Redirect` action.

    - `build()`: Finally build the xml


For more information, please read [http://docs.africastalking.com/voice](http://docs.africastalking.com/voice)


### `TokenService`

- `createCheckoutToken(String phoneNumber)`: Create a new checkout token for `phoneNumber`.

- `generateAuthToken()`: Generate an auth token to use for authentication instead of an API key.

### `USSDService`
For more information, please read [http://docs.africastalking.com/ussd](http://docs.africastalking.com/ussd)

## Issues

If you find a bug, please file an issue on [our issue tracker on GitHub]