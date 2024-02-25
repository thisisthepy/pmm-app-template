import PackageDescription

let package = Package(
	name: "PythonFFI",
	products: [
		.library(
			name: "PythonFFI",
			type: .static,
			targets: ["PythonFFI"])
	],
	dependencies: [],
	targets: [
		.target(
			name: "PythonFFI",
			dependencies: [],
			path: "PythonFFI")
	]
)