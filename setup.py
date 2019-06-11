from setuptools import find_packages, setup

setup(
    name="sentient_analysis",
    version="0.1.0",
    description="",
    url="",
    # setup_requires=["pytest-runner"],
    # tests_require=["pyteset"],
    packages=find_packages("src/python"),
    package_dir={"": "src/python"},
    install_requires=[
        "protobuf",
        "pyzmq",
    ],
)
