# ● Custom Unknow Command Message ●

A simple plugin that allows users to replace the "Unknown or incomplete command..." message with your own custom message.\
Support from: 1.20+

![img.png](img.png)

To something like this:

![img_1.png](img_1.png)

And even support multiple randomized messages:

![img_2.png](img_2.png)

All customizable by your own taste.

## ● Installation ●
- Simply download the plugin and drop it in your paper's (or fork) plugins folder.

## ● Command ●

| Command     | Description            | Permission |
|-------------|------------------------|------------|
| /cucmreload | Reload the config file | cucm.admin |

## ● Config ●

| Option                | Description                                                                                                                         | Default                                                                   |
|-----------------------|-------------------------------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------|
| single-message        | The message that will replace the "Unknown or incomplete command..." message                                                        | <red>Unknown Command, please use <gold>/help</gold> for more information. |
| use-multiple-messages | Toggling this option will ignore the single-message and make the plugin chose a random message from the list everytime it is called | false                                                                     |
| message-list          | There is no limit to the number of messages in the list, you can add or remove as much as you want                                  |                                                                           |