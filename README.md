# Smart AML Modular Monolith — Project Prompt

This repository will contain the Smart AML Modular Monolith project.

The canonical project specification has been added in the file Smart_AML_Modular_Monolith_Prompt.md.

Generation plan

1. Generate modules in the order defined in the prompt (SESSION 1 → smart-aml-shared, SESSION 2 → smart-aml-infrastructure, ...).
2. Create one module per session, run tests, fix compile issues iteratively.
3. Optionally produce a full zip of the generated project when all modules are created.

How I'll proceed next

- I will generate the smart-aml-shared module first (SECTION 4 of the prompt). This module is a pure Java library (no Spring) and required as context for other modules.
- I will push the generated module files to the repository in a single commit to the main branch.

If you prefer a different branch (feature branch) or want a zip file directly, reply and I will adjust the plan.

---

NOTE: The full project is large. To avoid timeouts and iterations, I will generate modules one at a time and push them in separate commits. This allows compilation and unit tests to be run module-by-module.
