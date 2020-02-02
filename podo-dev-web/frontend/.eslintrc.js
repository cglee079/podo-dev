module.exports = {
    root: true,
    env: {
        browser: true,
        node: true
    },
    parserOptions: {
        parser: "babel-eslint"
    },
    extends: ["plugin:vue/essential", "eslint:recommended"],

    plugins: ["vue"],

    rules: {
        "arrow-parens": 0,
        "no-undef": 0,
        "no-useless-escape": 0,
        "no-console": 0,
        semi: [2, "always"],
        indent: ["error", 4, { SwitchCase: 1 }],
        "vue/html-indent": [
            "error",
            4,
            {
                attribute: 1,
                closeBracket: 0,
                baseIndent: 1,
                ignores: []
            }
        ],
        "vue/script-indent": ["error", 4, { baseIndent: 0, switchCase: 1 }]
    }
};
