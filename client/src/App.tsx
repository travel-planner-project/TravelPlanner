import { RouterProvider, createBrowserRouter } from 'react-router-dom'
import { routerData } from './router/routerData'

const router = createBrowserRouter(
  routerData.map(routerElement => ({
    path: routerElement.path,
    element: routerElement.element,
  }))
)

function App() {
  return <RouterProvider router={router} />
}

export default App
