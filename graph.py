
import matplotlib.pyplot as plt

def plotErrors( error1, error2, error3, title ):
	plt.figure

	plt.plot(error1, label="Error 1")
	plt.plot(error2, label="Error 2")
	plt.plot(error3, label="Error 3")

	plt.xlabel("Iteration")
	plt.ylabel("Error")
	plt.title(title)

	plt.legend(loc="upper right")

	plt.savefig(title + ".png")
	plt.close()

	return
